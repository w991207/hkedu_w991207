package com.hk.board.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hk.board.command.AddUserCommand;
import com.hk.board.command.LoginCommand;
import com.hk.board.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/user")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value = "/addUser")
	public String addUser(Model model) {
		System.out.println("회원가입폼 이동");
		
		//회원가입폼에서 addUserCommand객체를 사용하는 코드가 작성되어 있어서 
		//null일경우 오류가 발생하기때문에 보내줘야 함
		model.addAttribute("addUserCommand", new AddUserCommand());
		
		return "member/addUserForm";
	}
	
	@PostMapping(value="/addUser")
	public String addUser(@Validated AddUserCommand addUserCommand, BindingResult result, Model model) {
		System.out.println("회원가입하기");
		
		if(result.hasErrors()) {
			System.out.println("회원가입 유효값 오류");
			return "member/addUserForm";
		}
		try {
			memberService.addUser(addUserCommand);
			System.out.println("회원가입 성공");
			return "redirect:/";
		} catch (Exception e) {
			System.out.println("회원가입실패");
			e.printStackTrace();
			return "redirect:addUser";
		}
		
	}
	
	@ResponseBody
	@GetMapping(value="/idChk")
	public Map<String, String> idChk(String id){
		System.out.println("id 중복체크");
		String resultId = memberService.idChk(id);
		//json 객체로 보내기 위해 map에 담아서 응답
		//text라면 그냥 String으로 보내도 됨
		Map<String,String>map=new HashMap<>();
		map.put("id", resultId);
		return map;
	}
	
	
	// 로그인 폼 이동
	@GetMapping(value = "/login")
	public String loginForm(Model model) {
		  model.addAttribute("loginCommand", new LoginCommand());
	   return "member/login";
	}
	
	@PostMapping(value = "/login")
	public String login(@Validated LoginCommand loginCommand, BindingResult result, Model model, HttpServletRequest request) {
	   if(result.hasErrors()) {
	      System.out.println("로그인 유효값 오류");
	      return "member/login";
	   }
	   String path = memberService.login(loginCommand, request, model);
	   
	   return "path";
	}
	
	@GetMapping(value="/logout")
	public String logout(HttpServletRequest request) {
		System.out.println("로그아웃");
		request.getSession().invalidate();
		return "redirect:/";
	}
	
	
	
	

}
