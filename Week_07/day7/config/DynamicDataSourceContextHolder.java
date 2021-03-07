package com.lxtyp.basic.config;

/**
 * @ClassName DynamicDataSourceContextHolder
 * @Date 2021/3/7 06:36
 * @Description TODO
 * @Status ISFINISH
 */
/**
 *	将上面的获取数据源独立出来了
 */
public class DynamicDataSourceContextHolder
{

  /**
   * ThreadLocal是一个变量，它用来保持一些数据，在这里它存的是我们定义的数据源名称
   * 每个线程在访问它时会各自在线程内建立一个它的副本，之后只对这个副本进行操作
   * 不会发生扰乱线程安全的问题
   */
  private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

  /**
   * 设置数据源的变量
   */
  public static void setDataSourceType(String dsType)
  {
    CONTEXT_HOLDER.set(dsType);
  }

  /**
   * 获得数据源的变量
   */
  public static String getDataSourceType()
  {
    return CONTEXT_HOLDER.get();
  }

  /**
   * 清空数据源变量
   */
  public static void clearDataSourceType()
  {
    CONTEXT_HOLDER.remove();
  }
}

