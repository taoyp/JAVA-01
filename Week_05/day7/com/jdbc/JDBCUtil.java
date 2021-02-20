package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ClassName JDBCUtil
 * @Date 2021/2/20 11:10
 * @Description TODO
 * @Status ISFINISH
 */
public class JDBCUtil {
  static {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    try {
      String url="jdbc:mysql://localhost:3306/lxtyp?useUnicode=true&characterEncoding=UTF8";
      String user="lxtyp";
      String password = "lxtyp";
      return DriverManager.getConnection(url,user,password);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }
}
