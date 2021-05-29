package com.honsoft.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsoft.web.entity.Order;
import com.honsoft.web.entity.User;
import com.honsoft.web.repository.mysql.OrderRepository;
import com.honsoft.web.repository.mysql.UserRepository;

@Service
public class UserOrdersService
{
    @Autowired
    private OrderRepository orderRepository;
 
    @Autowired
    private UserRepository userRepository;
 
    @Transactional(transactionManager="mysqlTransactionManager")
    public List<User> getUsers()
    {
        return (List<User>) userRepository.findAll();
    }
 
    @Transactional(transactionManager="mysqlTransactionManager")
    public List<Order> getOrders()
    {
        return (List<Order>) orderRepository.findAll();
    }
}