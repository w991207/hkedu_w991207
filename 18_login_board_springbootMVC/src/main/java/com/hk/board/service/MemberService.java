package com.hk.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.hk.board.command.AddUserCommand;
import com.hk.board.command.LoginCommand;
import com.hk.board.dtos.MemberDto;
import com.hk.board.mapper.MemberMapper;
import com.hk.board.status.RoleStatus;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor lombok기능 : final 필드를 초기화
@Service
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean addUser(AddUserCommand addUserCommand) {
		MemberDto mdto=new MemberDto();
		mdto.setId(addUserCommand.getId());
		mdto.setName(addUserCommand.getName());
		mdto.setPassword(passwordEncoder.encode(addUserCommand.getPassword()));
		mdto.setEmail(addUserCommand.getEmail());
		mdto.setAddress(addUserCommand.getAddress());
		mdto.setRole(RoleStatus.USER+"");
		
		return memberMapper.addUser(mdto);
	}
	
	public String idChk(String id) {
		return memberMapper.idChk(id);
	}
	
	public String login(LoginCommand loginCommand, HttpServletRequest request, Model model) {
		MemberDto dto = memberMapper.loginUser(loginCommand.getId());
		String path="home";
		if(dto!=null) {
			if(passwordEncoder.matches(loginCommand.getPassword(), dto.getPassword())) {
				//session객체에 로그인 정보 저장
				request.getSession().setAttribute("mdto", dto);
				return path;
			}else {
				System.out.println("패스워드 틀림");
				model.addAttribute("msg", "비번확인안하냐?");
				path="member/login";
			}
		}else {
			System.out.println("회원이아닙니다.");
			model.addAttribute("msg", "똑바로 안칠래?");
			path="member/login";
		}
		return path;
	}
}
