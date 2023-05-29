package com.oembed.parser.common.message;

/**
 * 로그 및 response에 탑재할 메시지 사전 정의
 * 
 * @author 이민우
 *
 */
public class CommonMessage {
	
	// 함수 호출 메시지
	public static String METHOD_EXEC = "@@ {}.{} 함수가 실행됩니다.";
	// 함수 에러 메시지
	public static String METHOD_FAIL = "@@ {}.{} 함수에서 에러가 발생했습니다: {}";
	
	// 성공 메시지
	public static String FORWARD_SUCCESS = "oEmbed 데이터를 조회했습니다.";
	
	// 에러 메시지
	public static String NULL_DATA = "oEmbed를 확인할 URL을 입력하세요.";
	public static String NOT_ALLOWED_DATA = "지원하지 않는 oEmbed입니다.";
	public static String TARGET_SERVER_ERROR = "oEmbed 서버에 문제가 존재합니다.";
	public static String INTERNAL_ERROR = "알 수 없는 에러가 발생했습니다.";
	
}
