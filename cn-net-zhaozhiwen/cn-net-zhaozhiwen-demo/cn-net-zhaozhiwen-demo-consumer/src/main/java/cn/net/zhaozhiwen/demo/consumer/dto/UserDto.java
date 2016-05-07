package cn.net.zhaozhiwen.demo.consumer.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDto implements Serializable{
	private Integer id;
	private String name;
	private Integer age;
	
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
