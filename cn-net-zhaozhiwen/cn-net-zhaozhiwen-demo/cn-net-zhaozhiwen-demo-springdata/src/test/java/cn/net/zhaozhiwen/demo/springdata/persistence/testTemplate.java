package cn.net.zhaozhiwen.demo.springdata.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.net.zhaozhiwen.demo.springdata.persistence.dao.StudentDao;
import cn.net.zhaozhiwen.demo.springdata.persistence.dao.TeacherDao;
import cn.net.zhaozhiwen.demo.springdata.persistence.entity.Student;
import cn.net.zhaozhiwen.demo.springdata.persistence.entity.Teacher;

/**
 * @date 2014-11-24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:demo-springdata-persistence.xml" })
public class testTemplate {

	private static Logger LOG = LoggerFactory.getLogger(testTemplate.class);

	@PersistenceContext
	EntityManager em;

	
	@Autowired
	TeacherDao tearchDao;
	
	@Autowired
	StudentDao studentDao;
	
	@Test
	public void exportORMapping() {
		/*Set<EntityType<?>> entities = em.getEntityManagerFactory().getMetamodel().getEntities();
		for (EntityType<?> entityType : entities) {
			String name = entityType.getName();
			try {
				em.createQuery("from " + name).getResultList();
				LOG.info("ok: " + name);
			} catch (Exception e) {
				LOG.error("init error " + name,e);
			}
		}*/
		
		//创建一个老师，关联三个学生
		/*
		Teacher teacher = new Teacher();
		teacher.setName("kevin1");
		List<Student> students = new ArrayList<Student>();
		Student s1 = new Student();
		s1.setName("s1");
		s1.setTearcher(teacher);
		Student s2 = new Student();
		s2.setName("s2");
		s2.setTearcher(teacher);
		Student s3 = new Student();
		s3.setName("s3");
		s3.setTearcher(teacher);
		students.add(s3);
		students.add(s2);
		students.add(s1);
		teacher.setStudents(students);
		tearchDao.save(teacher);*/
		
		//用不同的方式查找对应老师的学生列表
		Teacher t = tearchDao.findByName("kevin1");
		System.out.println(t.getStudents().size());
		List<Student> students = studentDao.listStudentByTeacherName("kevin1");
		System.out.println(students.size());
		students = studentDao.findByTeacher_Name("kevin1");
		System.out.println(students.size());
		for(Student s:students){
			System.out.println(s.getTeacher());
			System.out.println(s.getName()+" "+s.getTeacher().getName());
		}
		
		//创建10个老师，用于测试分页查询
		//级联查询测试
	}
}
