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
		}
		*/
//##创建实体#######################
		//1.创建一个老师，关联三个学生
		
		Teacher teacher = new Teacher();
		teacher.setName("kevin1");
		List<Student> students = new ArrayList<Student>();
		Student s1 = new Student();
		s1.setName("s1");
		s1.setTeacher(teacher);
		Student s2 = new Student();
		s2.setName("s2");
		s2.setTeacher(teacher);
		Student s3 = new Student();
		s3.setName("s3");
		s3.setTeacher(teacher);
		students.add(s3);
		students.add(s2);
		students.add(s1);
		teacher.setStudents(students);
		tearchDao.save(teacher);
		//删除一个老师
		tearchDao.delete(1);
		
		//2.创建20个老师，用于测试分页查询
		List<Teacher> teachers = new ArrayList<Teacher>();
		List<Student> students = new ArrayList<Student>();
		for(int i=0;i<20;i++){
			Teacher teacher = new Teacher();
			teacher.setName("teacher"+(i+1));
			students.clear();

			for(int j=0;j<10;j++){
				Student s = new Student();
				s.setName(teacher.getName()+"---student"+(j+1));
				s.setTeacher(teacher);
				students.add(s);
				
			}
			teacher.setStudents(students);
			tearchDao.save(teacher);
		}
		
		//for the teacher1 add 2 students
	
		Teacher t1 = tearchDao.findFirstByName("teacher1");
		Teacher t2 = tearchDao.findFirstByNameIgnoreCase("Teacher1");
		System.out.println(t1.getName());
		System.out.println(t2.getName());
		
		Teacher tt1 = tearchDao.queryByQuerySQL("teacher1");
		Teacher tt2 = tearchDao.queryByQuerySQL2("teacher1");
		System.out.println(tt1.getName());
		System.out.println(tt2.getName());
		//本地查询
		Teacher ttt1 = tearchDao.queryByNativeSQL(5);
		System.out.println(ttt1.getName());
		String ttt2 = tearchDao.queryByNativeSQL2(5);
		System.out.println(ttt2);
//列表查询
		List<Teacher> teacherLst =  tearchDao.findByNameIn("teacher2","teacher4","teacher10");
		for(Teacher t:teacherLst){
			System.out.println(t.getName());
		}
		teacherLst =  tearchDao.findByNameInOrderByNameDesc("teacher2","teacher4","teacher10");
		for(Teacher t:teacherLst){
			System.out.println(t.getName());
		}
		
		List<Teacher>teacherLst =   tearchDao.findByNameInAndId("teacher6",1);
		System.out.println(teacherLst.size());
//##带分页查询#######################
		//query the pageable list
		//1 无条件分页查询
		Pageable page = new PageRequest(0,10);
		Page<Teacher> teacherPage = tearchDao.findAll(page);
		for(Teacher teacher:teacherPage.getContent()){
			System.out.println(teacher.getId()+"  "+teacher.getName());
		}
		//2带条件分页查询
	
		Specification<Teacher> spec  = new Specification<Teacher>(){
			@Override
			public Predicate toPredicate(Root<Teacher> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> linkedList = new ArrayList<Predicate>();
				Path namePath= (Path)root.get("name");
				linkedList.add(cb.like(namePath, "teacher%"));
				return cb.and(linkedList.toArray(new Predicate[0]));
			}
		};
	
		Page<Teacher> teacherPage = tearchDao.findAll(spec, page);
		for(Teacher teacher:teacherPage.getContent()){
			System.out.println(teacher.getId()+"  "+teacher.getName());
		}
		//3无条件带排序查询
		Sort sort = new Sort(Direction.ASC,"name");
		page = new PageRequest(0,10,sort);
		
		teacherPage = tearchDao.findAll(page);
		for(Teacher teacher:teacherPage.getContent()){
			System.out.println(teacher.getId()+"  "+teacher.getName());
		}
		//4带条件带排序查询
		teacherPage = tearchDao.findAll(spec, page);
		for(Teacher teacher:teacherPage.getContent()){
			System.out.println(teacher.getId()+"  "+teacher.getName());
		}
		System.out.println(String.format("total_record:%d,total_pages:%d,current_page:%d,per_page_size:%d", teacherPage.getTotalElements(),
				teacherPage.getTotalPages(),teacherPage.getNumber(),teacherPage.getSize()));
//##count#######################
		/*long cnts = tearchDao.count();
		System.out.println("老师表总数:"+cnts);
		
		cnts = tearchDao.count(spec);
		System.out.println("老师名字以user开头的总数:"+cnts);
		
		cnts = tearchDao.countByNameLike("teacher%");
		System.out.println("老师名字以user开头的总数:"+cnts);
		
		cnts = tearchDao.countByNameNotLike("teacher%");
		System.out.println("老师名字不以user开头的总数:"+cnts);
		
		cnts = tearchDao.countByName("kevin1");
		System.out.println("名字为kevin1的老师的数量:"+cnts);*/
		
		
		
		//eg.用不同的方式查找对应老师的学生列表
		/*Teacher t = tearchDao.findByName("teacher1");
		System.out.println(t.getId());
		tearchDao.delete(5);
		List<Student> students = studentDao.listStudentByTeacherName("teacher1");
		System.out.println(students.size());
		students = studentDao.findByTeacher_Name("teacher1");
		System.out.println(students.size());
		for(Student s:students){
			System.out.println(s.getTeacher());
			System.out.println(s.getName()+" "+s.getTeacher().getName());
		}*/
		
		
		/*Student s1 = studentDao.queryNativeSQL3(10);
		if(null != s1){
			System.out.println(s1.getTeacher());			
		}else{
			System.out.println("s1 is null");
		}*/

		
		/*Object[][] voLst = tearchDao.queryByTSVO(16);
		System.out.println(null==voLst?"null":voLst.length);
		for(Object[] vo : voLst){
			System.out.println(vo[0] + " " + vo[1] + " "+vo[2]+" "+vo[3]);
		} */
		
	}
}
