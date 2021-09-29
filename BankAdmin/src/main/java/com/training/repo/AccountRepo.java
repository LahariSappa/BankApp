package com.training.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.bean.Account;

public interface AccountRepo extends JpaRepository<Account, Integer>{

}
