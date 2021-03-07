package com.lxtyp.basic.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName MybatisConfig
 * @Date 2021/3/7 19:22
 * @Description TODO
 * @Status ISFINISH
 */
@Configuration
public class MybatisConfig {
  @Autowired
  @Qualifier("masterDataSource")
  public DataSource masterDataSource;

  @Autowired
  @Qualifier("slave1DataSource")
  public DataSource slave1DataSource;

  @Autowired
  @Qualifier("slave2DataSource")
  public DataSource slave2DataSource;

  //声明动态数据源,默认值为 masterDataSource
  @Bean("dynamicDataSource")
  @Primary
  public DynamicDataSource dynamicDataSource(){
    //动态数据源集合
    Map<Object, Object> targetDataSourcesMap = new HashMap<>(2);
    targetDataSourcesMap.put(DataSourceType.MASTER.name(), masterDataSource);
    targetDataSourcesMap.put(DataSourceType.SLAVE1.name(), slave1DataSource);
    targetDataSourcesMap.put(DataSourceType.SLAVE2.name(), slave2DataSource);
    DynamicDataSource dynamicDataSource = new DynamicDataSource(masterDataSource, targetDataSourcesMap);

    return dynamicDataSource;
  }
}
