import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.net.zhaozhiwen.demo.consumer.dto.UserDto;
import cn.net.zhaozhiwen.demo.consumer.service.TestRegistryService;

public class Consumer {  
  
    public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                new String[] { "cn-jufuns-demo-web.xml" });  
        context.start();  
  
       TestRegistryService demoService = (TestRegistryService) context.getBean("testRegistryService");  
        UserDto user = demoService.find(1);  
        System.out.println(user.getName());  
  

        System.in.read();  
    }  
  
}  
