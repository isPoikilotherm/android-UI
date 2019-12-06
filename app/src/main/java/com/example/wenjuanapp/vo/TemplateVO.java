package com.example.wenjuanapp.vo;

public class TemplateVO {
	private int template_id;
	private String template_name;
	private String template_direction;
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getTemplate_direction() {
		return template_direction;
	}
	public void setTemplate_direction(String template_direction) {
		this.template_direction = template_direction;
	}
	public TemplateVO(Integer template_id, String template_name,String template_direction){
		this.template_id=template_id;
		this.template_name=template_name;
		this.template_direction=template_direction;
	}
	

}
