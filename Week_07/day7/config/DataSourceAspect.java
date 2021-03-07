package com.lxtyp.basic.config;

import com.lxtyp.basic.annotation.ReadOnly;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName DataSourceAspect
 * @Date 2021/3/7 06:46
 * @Description TODO
 * @Status ISFINISH
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect
{
  @Pointcut("@annotation(com.lxtyp.basic.annotation.ReadOnly)"
      + "|| @within(com.lxtyp.basic.annotation.ReadOnly)")
  public void dsPointCut()  {
    System.out.println("=============dsPointCut");
  }

  @Around("dsPointCut()")
  public Object around(ProceedingJoinPoint point) throws Throwable
  {
    // 获取自定义注解中的数据源名称参数
    MethodSignature signature = (MethodSignature) point.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method agentMethod = methodSignature.getMethod();
    ReadOnly readOnly = agentMethod.getAnnotation(ReadOnly.class);
    if (readOnly!=null&&!"".equals(readOnly))
    {
      int random = (int)(Math.random() * 2);
      DataSourceType dsType = DataSourceType.SLAVE1;
      if (random == 1) {
        dsType = DataSourceType.SLAVE2;
      }
      System.out.println("dsType===="+dsType.name());
      // 在线程变量中设置变量名，之后与mysql交互获取数据源链接时，用的就是这个数据源了。
      DynamicDataSourceContextHolder.setDataSourceType(dsType.name());
    } else {
      DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.MASTER.name());
    }

    try
    {
      return point.proceed();
    }
    finally
    {
      // 销毁数据源 在执行方法之后
      DynamicDataSourceContextHolder.clearDataSourceType();
    }
  }

}
