package cn.net.zhaozhiwen.demo.springdata.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.net.zhaozhiwen.demo.springdata.persistence.entity.Teacher;

/**
 * 继承JpaRepository
 * @author zhaozhiwen
 *
 */
public interface TeacherDao extends JpaRepository<Teacher, Integer>, JpaSpecificationExecutor<Teacher> {
	
	Teacher findByName(String teacherName);
	
	

	
}
