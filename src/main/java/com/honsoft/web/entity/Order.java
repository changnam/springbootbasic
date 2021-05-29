package com.honsoft.web.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="ORDERS")
public class Order
{
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(nullable=false, name="cust_name")
    private String customerName;
    @Column(nullable=false, name="cust_email")
    private String customerEmail;
 
    @OneToMany(mappedBy="order")
    private Set<OrderItem> orderItems;
 
   
}