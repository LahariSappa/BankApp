package com.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.training.bean.Account;
import com.training.bean.BankCustomer;
import com.training.config.AdminConfiguration;
import com.training.repo.AccountRepo;
import com.training.repo.CustomerRepo;
import com.training.repo.TransactionRepo;

@SpringBootApplication
public class BankAdminApplication implements CommandLineRunner{

	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private TransactionRepo transactionRepo;
	@Autowired
	private AdminConfiguration config;
	
	public static void main(String[] args) {
		SpringApplication.run(BankAdminApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/*
		 * Account account = new Account(); account.setBalance(7500);
		 * 
		 * BankCustomer customer=new BankCustomer(); customer.setName("peter");
		 * customer.setAddress("bangalore"); customer.setEmailId("peter@xyz.com");
		 * customer.setPhoneno("840972474"); customer.setAccount(account);
		 * customerRepo.save(customer);
		 */
		 
	
		
	}

}
