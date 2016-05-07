package cn.net.zhaozhiwen.demo.springdata.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name="t_student")
@Entity
public class Student implements java.io.Serializable {

	private int id;
	private String name;
	
	private Teacher teacher;
	
	private List<Subject> subjects;
	
	@Id @GeneratedValue
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	@OneToMany()
	@Column(name="subject_id")
	public List<Subject> getSubjects() {
		return subjects;
	}
	
	
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	
	@ManyToOne()
	@JoinColumn(name="teacher_id",nullable=true)
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	
	
}
