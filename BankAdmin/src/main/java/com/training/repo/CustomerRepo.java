package com.training.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.bean.BankCustomer;

public interface CustomerRepo extends JpaRepository<BankCustomer, Integer>{

}
