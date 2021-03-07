package com.lxtyp.basic.generator.dao;

import com.lxtyp.basic.generator.entity.Order;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(String ORDER_ID);

    int insert(List<Order> list);

    Order selectByPrimaryKey(String ORDER_ID);

    Order selectByInfo();

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);
}