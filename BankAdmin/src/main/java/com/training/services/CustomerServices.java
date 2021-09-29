package com.training.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.bean.Account;
import com.training.bean.BankCustomer;
import com.training.bean.Transaction;
import com.training.repo.AccountRepo;
import com.training.repo.CustomerRepo;
import com.training.repo.TransactionRepo;

@Service
public class CustomerServices {
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private TransactionRepo transactionRepo;
	
	private List<Account> accounts;

	public Account amount(int accountId)
	{
		Optional<Account> opAccount = accountRepo.findById(accountId);
		if(opAccount.isPresent())
			return opAccount.get();
		return null;
	}
	
	public BankCustomer customerDetails(int custId)
	{
		Optional<BankCustomer> opCustomer = customerRepo.findById(custId);
		if(opCustomer.isPresent())
			return opCustomer.get();
		return null;
	}
	public Transaction addTransaction(Transaction transaction)
	{
		Optional<Account> opAccount1 = accountRepo.findById(transaction.getFromAccount());
		Optional<Account> opAccount2 = accountRepo.findById(transaction.getToAccount());
		String type =transaction.getTransactionType();
		double bal=transaction.getAmount();
		Account a2=opAccount2.get();
		Account a1=opAccount1.get();
			if(opAccount1.isPresent() && opAccount2.isPresent())
			{
				if(type.equalsIgnoreCase("credit"))
				{
					a2.setBalance(a2.getBalance()+transaction.getAmount());
					a1.setBalance(a1.getBalance()-transaction.getAmount());
				}
				if(type.equalsIgnoreCase("debit"))
				{
					a1.setBalance(a1.getBalance()+transaction.getAmount());
					a2.setBalance(a2.getBalance()-transaction.getAmount());
				}
			}
		return transactionRepo.save(transaction);
		
	}

	public Account viewAccount(int accountId) {
		Optional<Account> opAccount = accountRepo.findById(accountId);
		if(opAccount.isPresent())
			return opAccount.get();
		return null;
	}
	
}
