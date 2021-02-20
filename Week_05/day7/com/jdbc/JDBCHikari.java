package com.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * @ClassName JDBCHikari
 * @Date 2021/2/20 18:47
 * @Description TODO
 * @Status ISFINISH
 */
public class JDBCHikari {

  public static void main(String[] args) {
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    Random random = new Random();
    try {
      connection = JDBCUtil.getConnection();
      statement = connection.createStatement();
      int age = random.nextInt(100);
      statement.execute("insert into tf_f_user ( create_date, user_age, user_desc, user_name) values ( now(), " + age + ", 'my age now is " + age + "', 'geek test')");

      int count = statement.executeUpdate("update tf_f_user set user_age = " + random.nextInt(100) + " where user_id = 1");
      System.out.println("update count = " + count);

      resultSet = statement.executeQuery("select user_age from tf_f_user where user_id = 1");
      while (resultSet.next()) {
        System.out.println(resultSet.getInt("user_age"));
      }

      int count2 = statement.executeUpdate("delete from tf_f_user where user_id = 4");
      System.out.println("delete count = " + count);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/lxtyp?useUnicode=true&characterEncoding=UTF8");
    hikariConfig.setUsername("lxtyp");
    hikariConfig.setPassword("lxtyp");
    hikariConfig.setAutoCommit(true);
    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "512");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "1024");
    HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

    try {
      return hikariDataSource.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }
}
