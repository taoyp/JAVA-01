package com.lxtyp.geek.controller;

import com.lxtyp.geek.bean.AnnoPerson;
import com.lxtyp.geek.bean.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Date 2021/2/19 19:11
 * @Description TODO
 * @Status ISFINISH
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

  /**
   * XML方式配置
   * */
  @RequestMapping(value = "/xml")
  @ResponseBody
  public People genXml() {
    ApplicationContext context = new ClassPathXmlApplicationContext("classpath:bean/spring-bean.xml");
    People people = (People) context.getBean("people");
    return people;
  }

  @Autowired
  AnnoPerson annoPerson;

  /**
   * XML方式配置
   * */
  @RequestMapping(value = "/anno")
  @ResponseBody
  public String genAnnotation() {
    return annoPerson.getAnnotationName();
  }
}
