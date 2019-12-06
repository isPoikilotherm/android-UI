package com.example.wenjuanapp.vo;

public class ProjectVO {
	private int project_id;
	private int user_id;
	private String name;
	private String direction;
	private String star_time;
	private String end_time;
	private int can_answer;
	
	public ProjectVO() {
		super();
	}
	public ProjectVO(int project_id,int user_id,String name,String direction,String end_time,int can_answer) {
		super();
		this.project_id=project_id;
		this.user_id=user_id;
		this.name=name;
		this.direction=direction;
		this.end_time=end_time;
		this.can_answer=can_answer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public int getCan_answer() {
		return can_answer;
	}
	public void setCan_answer(int can_answer) {
		this.can_answer = can_answer;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "ProjectVO [project_id=" + project_id + ", user_id=" + user_id + ", name=" + name + ", direction="
				+ direction +  ", end_time=" + end_time + ", can_answer=" + can_answer
				+ "]";
	};
	
}
