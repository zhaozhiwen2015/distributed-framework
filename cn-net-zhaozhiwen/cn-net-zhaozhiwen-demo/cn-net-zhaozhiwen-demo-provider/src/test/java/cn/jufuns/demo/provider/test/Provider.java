package cn.jufuns.demo.provider.test;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("demo-dubbo-provider.xml"); 
		
		applicationContext.start();
		System.in.read();
		
		applicationContext.close();
	}

}
