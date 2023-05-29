package com.oembed.parser.oembed.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.oembed.parser.common.message.CommonMessage;
import com.oembed.parser.common.vo.ResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OembedService {
	
	@Value("#{${oembed.source}}")
	private Map<String, String> oembedSourceMap;
	
	private String HTTPS = "https://";
	private String HTTP = "http://";
	private String WWW = "www.";
	
	/**
	 * 화면에 oembed source 목록을 출력하기 위한 함수
	 * @return
	 */
	public Set<String> getOembedSourceList() {
		return oembedSourceMap.keySet();
	}
	
	
	/**
	 * oembed 서버로 포워딩
	 * 
	 * @param targetUrl : oembed를 추출할 URL
	 * @return
	 */
	public ResponseVO forwardRequest(String targetUrl) {
		
		log.info(CommonMessage.METHOD_EXEC, "OembedService", "forwardRequest");
		
		ResponseVO response = new ResponseVO();
		response.setStatus(HttpStatus.OK);
		response.setMessage(CommonMessage.FORWARD_SUCCESS);
		
		// targetUrl이 입력되지 않았을 때.
		if(targetUrl == null) {
			log.error(CommonMessage.NULL_DATA);
			
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessage(CommonMessage.NULL_DATA);
			
			return response;
		}
		
		// targetUrl로부터 목표 sourceMap 확인
		String sourceUrl = getSourceUrl(targetUrl);
		if(sourceUrl == null) {
			// 지원하지 않는 source
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessage(CommonMessage.NOT_ALLOWED_DATA);
			
			return response;
		}
		
		// sourceUrl로 포워딩
		try {
			JsonNode result = sendHttpProtocol(sourceUrl + targetUrl);
			response.setData(result);
		}
		catch(RestClientException e) {
			// 연결 실패이므로 500
			log.warn(CommonMessage.METHOD_FAIL, "OembedService", "forwardRequest", e.getMessage());
			
			
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(CommonMessage.TARGET_SERVER_ERROR);
			
			// 상태 확인을 위해 data에 메시지 탑재
			response.setData(e.getMessage());
		}
		catch(Exception e) {
			// 알수 없는 에러이므로 500
			log.warn(CommonMessage.METHOD_FAIL, "OembedService", "forwardRequest", e.getMessage());
			
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(CommonMessage.INTERNAL_ERROR);
		}
		
		return response;
	}
	
	/**
	 * HTTP 통신 함수
	 * 
	 * @param targetUrl : 통신할 주소
	 * @return
	 */
	public JsonNode sendHttpProtocol(String targetUrl) {
		
		log.info(CommonMessage.METHOD_EXEC, "OembedService", "sendHttpProtocol");
		log.info(" > Request : {}", targetUrl);
		
		HttpMethod method = HttpMethod.GET;
		
		// 목표 oembed source에서 데이터 수신
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.exchange(targetUrl, method, new HttpEntity(new HttpHeaders()), JsonNode.class);
		JsonNode map = response.getBody();
		
		// GET인데 200이 아니라는 말은 에러가 발생했음을 의미.
		if(response.getStatusCode() != HttpStatus.OK) {
			log.error(" > Response : {}", response.getBody().toString());
			throw new RestClientException(response.getBody().toString());
		}
		
		log.info(" > Response : {}", map.toString());
		
		return map;
		
	}
	
	/**
	 * 입력된 URL로부터 목표 SOURCE의 URL 추출
	 * 
	 * @param targetUrl : 사용자에게 입력받은 URL
	 * @return
	 */
	public String getSourceUrl(String targetUrl) {
		log.info(CommonMessage.METHOD_EXEC, "OembedService", "getSourceUrl");
		
		// https:// 혹은 http:// 제거
		if(targetUrl.startsWith(HTTPS)) {
			targetUrl = targetUrl.replace(HTTPS, "");
		}
		else if(targetUrl.startsWith(HTTP)) {
			targetUrl = targetUrl.replace(HTTP, "");
		}
		
		// www. 제거
		if(targetUrl.startsWith(WWW)) {
			targetUrl = targetUrl.replace(WWW, "");
		}
		
		// / 이후 제거 후 메인 url 추출
		targetUrl = targetUrl.split("/")[0];
		
		for(String key : oembedSourceMap.keySet()) {
			if(targetUrl.contains(key)) {
				return oembedSourceMap.get(key);
			}
		}
		
		// 존재하지 않는 Source면 null 반환 후 예외처리
		return null;
		
	}
	
	
}