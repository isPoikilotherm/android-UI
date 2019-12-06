package com.example.wenjuanapp.vo;

public class ShortanswerVO {

	
	private Integer question_id;
	private Integer project_id;
	private String question_name;
	
	
	public Integer getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(Integer question_id) {
		this.question_id = question_id;
	}
	public Integer getProject_id() {
		return project_id;
	}
	public void setProject_id(Integer project_id) {
		this.project_id = project_id;
	}
	public String getQuestion_name() {
		return question_name;
	}
	public void setQuestion_name(String question_name) {
		this.question_name = question_name;
	}


	public ShortanswerVO(Integer question_id, Integer project_id, String question_name) {
		this.question_id = question_id;
		this.project_id = project_id;
		this.question_name = question_name;

	}
	
	
}
