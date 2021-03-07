package com.lxtyp.basic.config;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName DynamicDataSource
 * @Date 2021/3/7 06:33
 * @Description TODO
 * @Status ISFINISH
 */
public class DynamicDataSource extends AbstractRoutingDataSource
{
  public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources)
  {
    super.setDefaultTargetDataSource(defaultTargetDataSource);
    super.setTargetDataSources(targetDataSources);
    super.afterPropertiesSet();
  }

  //此方法的返回值决定具体从哪个数据源中获取连接
  @Override
  protected Object determineCurrentLookupKey()
  {
    return DynamicDataSourceContextHolder.getDataSourceType();
  }
}