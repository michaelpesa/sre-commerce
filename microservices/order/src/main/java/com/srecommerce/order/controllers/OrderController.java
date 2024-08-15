package com.srecommerce.order.controllers;

import com.srecommerce.order.domain.Order;
import com.srecommerce.order.domain.Product;
import com.srecommerce.order.repositories.OrderRepository;
import com.srecommerce.order.services.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found"));
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        Product product = productClient.getProductById(order.getProductId());
        if (product == null || !product.isAvailable() || product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Product not available");
        }
        order.setOrderDate(new java.util.Date());
        order.setStatus("PENDING");
        order.setPrice(product.getPrice() * order.getQuantity());
        return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order order = this.getOrderById(id);
        order.setProductId(orderDetails.getProductId());
        order.setQuantity(orderDetails.getQuantity());
        order.setPrice(orderDetails.getPrice());
        order.setStatus(orderDetails.getStatus());
        return orderRepository.save(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        Order order = this.getOrderById(id);
        orderRepository.delete(order);
    }
}
