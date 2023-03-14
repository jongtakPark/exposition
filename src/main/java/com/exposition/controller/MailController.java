package com.exposition.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exposition.entity.Member;
import com.exposition.service.MailService;
import com.exposition.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/mail")
@RequiredArgsConstructor
public class MailController {

	private final MailService mailService;
	private final MemberService memberService;
	
	//회원가입시 입력한 이메일로 인증번호 메일 발송
	@GetMapping(value="/sendmail")
	@ResponseBody
	public String mailAuth(String email) throws Exception {
	    String authKey = mailService.sendAuthMail(email); 
	    return authKey;
	}
	//이메일 인증확인
	@GetMapping(value="/checkcode")
	@ResponseBody
	public HashMap<String, Object> codeCheck(@RequestParam("emailcode") String emailcode) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", mailService.checkCode(emailcode));
		return map;	
	}
	
	
}
