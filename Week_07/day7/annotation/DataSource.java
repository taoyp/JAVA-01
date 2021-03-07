package com.lxtyp.basic.annotation;

import com.lxtyp.basic.config.DataSourceType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName DataSource
 * @Date 2021/3/7 07:11
 * @Description TODO
 * @Status ISFINISH
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

  DataSourceType name() default DataSourceType.MASTER;

}
