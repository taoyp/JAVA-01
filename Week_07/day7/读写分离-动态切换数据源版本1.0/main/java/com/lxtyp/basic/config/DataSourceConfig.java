package com.lxtyp.basic.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;

/**
 * @ClassName DataSourceConfig
 * @Date 2021/3/7 00:11
 * @Description TODO
 * @Status ISFINISH
 */
@Configuration
public class DataSourceConfig {

  //主数据源 master数据源
  @Bean(name = "masterDataSource")
  public DataSource masterDataSource(StandardEnvironment env) {
    String prefix = "spring.datasource.master";
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(env.getProperty(prefix + ".url"));
    hikariConfig.setUsername(env.getProperty(prefix + ".username"));
    hikariConfig.setPassword(env.getProperty(prefix + ".password"));
    return new HikariDataSource(hikariConfig);
  }

  public HikariConfig gen(StandardEnvironment env, String prefix) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(env.getProperty(prefix + ".url"));
    hikariConfig.setUsername(env.getProperty(prefix + ".username"));
    hikariConfig.setPassword(env.getProperty(prefix + ".password"));
    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "512");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "1024");
    hikariConfig.setAutoCommit(true);
    return hikariConfig;
  }

  //第二个数据源 slave1
  @Bean(name = "slave1DataSource")
  public DataSource slave1DataSource(StandardEnvironment env) {
    String prefix = "spring.datasource.slave1";
    return new HikariDataSource(gen(env, prefix));
  }

  //第三个数据源 slave2
  @Bean(name = "slave2DataSource")
  public DataSource slave2DataSource(StandardEnvironment env) {
    String prefix = "spring.datasource.slave2";
    return new HikariDataSource(gen(env, prefix));
  }

  // 动态数据源的实例化对象，相当于动态数据源切换的选择池
//  @Bean(name = "dynamicDataSource")
//  @Primary
//  public DynamicDataSource dataSource(DataSource masterDataSource)
//  {
//    Map<Object, Object> targetDataSources = new HashMap<>();
//    targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
//    // 将从数据源也放入其中
////    setDataSource(targetDataSources, DataSourceType.SLAVE1.name(), "slave1DataSource");
////    setDataSource(targetDataSources, DataSourceType.SLAVE2.name(), "slave2DataSource");
//    return new DynamicDataSource(masterDataSource, targetDataSources);
//  }

  /**
   * 将其他数据源一同放到targetDataSources中，
   * 这里只是把这一步单独提取出来而已
   */
//  public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName)
//  {
//    try
//    {
//      DataSource dataSource = (DataSource) BasicApplication.getContext().getBean(beanName);
//      targetDataSources.put(sourceName, dataSource);
//    }catch (Exception e){
//      // 配置从数据库失败
//      e.printStackTrace();
//    }
//  }


}
