package com.lxtyp.basic.config;

import com.lxtyp.basic.BasicApplication;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName DataSourceConfig
 * @Date 2021/3/7 00:11
 * @Description TODO
 * @Status ISFINISH
 */
@Configuration
public class DataSourceConfig {
  //主数据源配置 master数据源
  @Primary
  @Bean(name = "masterDsProperties")
  @ConfigurationProperties(prefix = "spring.datasource.master")
  public DataSourceProperties masterDsProperties() {
    return new DataSourceProperties();
  }

  //主数据源 master数据源
  @Primary
  @Bean(name = "masterDataSource")
  public DataSource masterDataSource(@Qualifier("masterDsProperties") DataSourceProperties dataSourceProperties) {
    return dataSourceProperties.initializeDataSourceBuilder().build();
  }

  //第二个数据源配置 slave1
  @Bean(name = "slave1DsProperties")
  @ConfigurationProperties(prefix = "spring.datasource.slave1")
  public DataSourceProperties slave1DsProperties() {
    return new DataSourceProperties();
  }

  //第二个数据源 slave1
  @Bean("slave1DataSource")
  public DataSource slave1DataSource(@Qualifier("slave1DsProperties") DataSourceProperties dataSourceProperties) {
    return dataSourceProperties.initializeDataSourceBuilder().build();
  }

  //第三个数据源配置 slave2
  @Bean(name = "slave2DsProperties")
  @ConfigurationProperties(prefix = "spring.datasource.slave2")
  public DataSourceProperties slave2DsProperties() {
    return new DataSourceProperties();
  }

  //第三个数据源 slave2
  @Bean("slave2DataSource")
  public DataSource slave2DataSource(@Qualifier("slave2DsProperties") DataSourceProperties dataSourceProperties) {
    return dataSourceProperties.initializeDataSourceBuilder().build();
  }

  // 动态数据源的实例化对象，相当于动态数据源切换的选择池
  @Bean(name = "dynamicDataSource")
  @Primary
  public DynamicDataSource dataSource(DataSource masterDataSource)
  {
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
    // 将从数据源也放入其中
    setDataSource(targetDataSources, DataSourceType.SLAVE1.name(), "slave1DataSource");
    setDataSource(targetDataSources, DataSourceType.SLAVE2.name(), "slave2DataSource");
    return new DynamicDataSource(masterDataSource, targetDataSources);
  }

  /**
   * 将其他数据源一同放到targetDataSources中，
   * 这里只是把这一步单独提取出来而已
   */
  public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName)
  {
    try
    {
      DataSource dataSource = (DataSource) BasicApplication.getContext().getBean(beanName);
      targetDataSources.put(sourceName, dataSource);
    }catch (Exception e){
      // 配置从数据库失败
      e.printStackTrace();
    }
  }
}
