package cn.net.zhaozhiwen.demo.consumer.service;

import cn.net.zhaozhiwen.demo.consumer.dto.UserDto;

public interface TestRegistryService {
   public String hello(String name);
   public UserDto find(Integer uid);
}