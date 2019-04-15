package com.shyam.azureblobstoragedemo.service;

import com.shyam.azureblobstoragedemo.model.Order;
import com.shyam.azureblobstoragedemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author SVadikari on 4/12/19
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public void saveOrder(Order order) {
        orderRepository.saveOrder(order);
    }

    public Order readOrder(final String orderId) {
        return orderRepository.getOrder(orderId);
    }
}
