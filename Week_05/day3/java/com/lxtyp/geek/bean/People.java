package com.lxtyp.geek.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName People
 * @Date 2021/2/19 19:06
 * @Description TODO
 * @Status ISFINISH
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class People {
  private String name;
  private int age;
}
