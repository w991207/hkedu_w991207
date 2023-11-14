package com.hk.board.command;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;


public class LoginCommand {
	
	@NotBlank(message = "아이디를 쳐야 로그인을 할거아니야")
	private String id;
	@NotBlank(message = "비밀번호도 안치고 뭐하는데")
	@Length(min = 8, max=16, message="8자리 이상, 16자리 이하로 입력하세요")
	private String password;
	public LoginCommand() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginCommand [id=" + id + ", password=" + password + "]";
	}
	public LoginCommand(@NotBlank(message = "아이디를 쳐야 로그인을 할거아니야") String id,
			@NotBlank(message = "비밀번호도 안치고 뭐하는데") @Length(min = 8, max = 16, message = "8자리 이상, 16자리 이하로 입력하세요") String password) {
		super();
		this.id = id;
		this.password = password;
	}
	
}
