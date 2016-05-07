package cn.net.zhaozhiwen.demo.persistence.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.net.zhaozhiwen.demo.persistence.entites.User;

@Repository
public interface UserMapper{
	
    User findUserById(@Param("id")Integer id);

   
}