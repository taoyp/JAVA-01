package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * @ClassName JDBCTransaction
 * @Date 2021/2/20 11:08
 * @Description TODO
 * @Status ISFINISH
 */
public class JDBCTransaction {

  public static void main(String[] args) {
    Connection connection;
    PreparedStatement statement;
    ResultSet resultSet;
    Random random = new Random();
    try {
      connection = JDBCUtil.getConnection();
      connection.setAutoCommit(false);

      int age = random.nextInt(100);
      statement = connection.prepareStatement("insert into tf_f_user ( create_date, user_age, user_desc, user_name) values ( now(), " + age + ", 'my age now is " + age + "', 'geek test')");
      statement.addBatch();
      statement.addBatch("update tf_f_user set user_age = " + random.nextInt(100) + " where user_id = 1");
      statement.addBatch("delete from tf_f_user where user_id = 3");
      int[] results = statement.executeBatch();
      for (int result:results) {
        System.out.println(result);
      }
      System.out.println();

      resultSet = statement.executeQuery("select user_id, user_age from tf_f_user");
      while (resultSet.next()) {
        System.out.println("user_id=" + resultSet.getInt("user_id") + "||age=" + resultSet.getInt("user_age"));
      }

      connection.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
