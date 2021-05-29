package com.honsoft.web.repository.h2;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.honsoft.web.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  List<Customer> findByLastName(String lastName);

  Customer findById(long id);
}