package io.kimmking.spring01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@ComponentScan({"com.*", "io.*"})
@Controller
public class Spring01Application {
	@Autowired
	Student student;

	public static void main(String[] args) {
		SpringApplication.run(Spring01Application.class, args);
	}

	@RequestMapping(value = "/student")
	@ResponseBody
	public Student getStudentInfo() {
		return student;
	}


	@Bean
	@ConditionalOnClass(Student.class)
	public Student getStudent() {
		return new Student().create();
	}
}
