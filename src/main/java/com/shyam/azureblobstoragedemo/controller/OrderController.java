package com.shyam.azureblobstoragedemo.controller;

import com.shyam.azureblobstoragedemo.model.AuditEvent;
import com.shyam.azureblobstoragedemo.model.Order;
import com.shyam.azureblobstoragedemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author SVadikari on 4/12/19
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public List<AuditEvent> orderAuditDetails(@PathVariable String orderId){
        return orderService.readOrder(orderId);
    }

    @PostMapping
    public ResponseEntity orderAudit(@RequestBody AuditEvent auditEvent){
        orderService.saveOrder(auditEvent);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity deleteOrderAudit(@PathVariable String orderId) {
        orderService.deleteOrderAudit(orderId);
        return ResponseEntity.ok().build();
    }
}
