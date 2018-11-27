package com.icefamer.orderserver.repository;

import com.icefamer.orderserver.OrderServerApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

@Component
public class OrderMasterRepositoryTest extends OrderServerApplicationTests {
    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void getAllTest(){
        System.out.println(repository.findAll().size());
    }

}