package cn.net.zhaozhiwen.demo.web.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.net.zhaozhiwen.demo.consumer.dto.UserDto;
import cn.net.zhaozhiwen.demo.consumer.service.TestRegistryService;



@Controller
public class IndexController {

	@Autowired
	TestRegistryService testRegistryService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index( Model model){     
		UserDto usr = testRegistryService.find(1);
		model.addAttribute("user", usr.getName());
		return "index";
	}
	

	
}