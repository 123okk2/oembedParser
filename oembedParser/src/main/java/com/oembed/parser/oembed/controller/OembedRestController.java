package com.oembed.parser.oembed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.oembed.parser.common.vo.ResponseVO;
import com.oembed.parser.oembed.service.OembedService;

/**
 * oembed 조회를 위한 백엔드 REST API
 * @author 이민우
 *
 */
@RestController
public class OembedRestController {
	
	@Autowired OembedService oembedService;
	
	/**
	 * 
	 * /oembed?targetUrl=https://~~ 와 같은 양식의 GET 수신
	 * 
	 * @param targetUrl
	 * @return
	 */
	@GetMapping("/oembed")
	public ResponseVO oembedSearch(@ModelAttribute("targetUrl") String targetUrl) {
		
		return oembedService.forwardRequest(targetUrl);
		
	}
}
