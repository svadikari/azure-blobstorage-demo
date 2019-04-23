package com.shyam.azureblobstoragedemo.service;

import com.shyam.azureblobstoragedemo.model.AuditEvent;
import com.shyam.azureblobstoragedemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SVadikari on 4/12/19
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(AuditEvent auditEvent) {
        orderRepository.saveOrder(auditEvent);
    }

    public List<AuditEvent> readOrder(final String orderId) {
        return orderRepository.getAuditOrderDetails(orderId);
    }

    public Boolean deleteOrderAudit(final String orderId) {
        return orderRepository.deleteOrderAuditDetails(orderId);
    }
}
