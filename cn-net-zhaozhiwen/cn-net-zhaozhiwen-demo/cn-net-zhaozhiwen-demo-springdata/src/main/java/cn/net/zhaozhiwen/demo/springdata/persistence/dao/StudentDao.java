package cn.net.zhaozhiwen.demo.springdata.persistence.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.net.zhaozhiwen.demo.springdata.persistence.entity.Student;

/**
 * 继承JpaRepository
 * @author zhaozhiwen
 *
 */
public interface StudentDao extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
	
	List<Student> findByTeacher_Name(String teacherName);
	
	List<Student> findByTeacher_Name(String teacherName,Pageable pageable);
	
	List<Student> findByTeacher_Name(String teacherName,Sort sort);
	
	
	@Query("from Student s where s.teacher.name = :xxx")
	List<Student> listStudentByTeacherName(@Param("xxx")String teacherName);
	
	Student findByName(String studentName);
	
}
