package com.icefamer.orderserver.service;


import com.icefamer.orderserver.dto.OrderDTO;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);
}
