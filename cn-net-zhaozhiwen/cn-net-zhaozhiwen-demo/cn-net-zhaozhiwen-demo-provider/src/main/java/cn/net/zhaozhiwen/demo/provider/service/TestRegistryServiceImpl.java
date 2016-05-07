package cn.net.zhaozhiwen.demo.provider.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.net.zhaozhiwen.demo.consumer.dto.UserDto;
import cn.net.zhaozhiwen.demo.consumer.service.TestRegistryService;
import cn.net.zhaozhiwen.demo.persistence.dao.UserMapper;
import cn.net.zhaozhiwen.demo.persistence.entites.User;


@Transactional
@Service
public class TestRegistryServiceImpl implements TestRegistryService {
	
	@Autowired
	UserMapper userMapper;
	public String hello(String name) {	
		User user = userMapper.findUserById(1);
	    System.out.println("hello"+user.getUsername()+"---");
		return "hello"+user.getUsername()+"---";
	}
	public UserDto find(Integer uid) {
		// TODO Auto-generated method stub
		User  user =  userMapper.findUserById(uid);
		UserDto userDto = new UserDto();
		userDto.setAge(user.getAge());
		userDto.setId(user.getId());
		userDto.setName(user.getUsername());
		return userDto;
	}
}