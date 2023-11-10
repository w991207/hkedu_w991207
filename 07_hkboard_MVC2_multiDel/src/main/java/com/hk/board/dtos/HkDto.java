package com.hk.board.dtos;

import java.io.Serializable;
import java.util.Date;

//DTO객체 : 데이터를 저장하고 운반하기 위한 객체
public class HkDto implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//Serializable: 직렬화
	private int seq;
	private String id;
	private String title;
	private String content;
	private Date regDate;
	
	public HkDto() {
		// TODO Auto-generated constructor stub
	}

	public HkDto(int seq, String id, String title, String content, Date regDate) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.regDate = regDate;
	}
	public HkDto(String id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
	public HkDto(int seq, String title, String content) {
		super();
		this.seq = seq;
		this.title = title;
		this.content = content;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "HkDto [seq=" + seq + ", id=" + id + ", title=" + title + ", content=" + content + ", regDate=" + regDate
				+ "]";
	}
	
}
