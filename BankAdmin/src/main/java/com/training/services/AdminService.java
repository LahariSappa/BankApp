package com.training.services;

import java.util.Date;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.training.bean.Account;
import com.training.bean.BankCustomer;
import com.training.bean.MyUser;
import com.training.bean.Transaction;
import com.training.repo.AccountRepo;
import com.training.repo.CustomerRepo;
import com.training.repo.TransactionRepo;
import com.training.repo.UserRepo;

@Service
public class AdminService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private TransactionRepo transactionRepo;
	
	private static final Logger log = LoggerFactory.getLogger(CsvExportService.class);


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Username Entered"+" " + username);
		
		MyUser user =null;
		Optional<MyUser> opUser = userRepo.findById(username);
		if(opUser.isPresent())
			user=opUser.get();
		
		System.out.println("Roles:"+user.getPassword()+" "+user.getRoles());
		List<GrantedAuthority> list;
		list = Arrays.stream(user.getRoles().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		return new User(user.getUsername(),user.getPassword(),list);
	}
	
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
		System.out.println(opCustomer);
		if(opCustomer.isPresent())
		{
			return opCustomer.get();
		}
		return null;
	}
	
	public String getPassword(String password)
	{
		BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		
		return hashedPassword;	
	}
	
	public Transaction transactionDetails(int fromAccount)
	{
		Optional<Transaction> opTransaction = transactionRepo.findById(fromAccount);
		System.out.println(opTransaction);
		if(opTransaction.isPresent())
		{
			return opTransaction.get();
		}
		return null;
	}
	
	public MyUser userDetails(String username)
	{
		Optional<MyUser> opUser = userRepo.findById(username);
		System.out.println(opUser);
		if(opUser.isPresent())
		{
			return opUser.get();
		}
		return null;
	}
	
	public Account delAccount(int accountId) {
		Account a1=amount(accountId);
		if(a1!=null)
			accountRepo.delete(a1);
		return null;
	} 
	
	public BankCustomer updateMail(int custId,String newemailId) {
		BankCustomer b1=customerDetails(custId);
		if(b1!=null)
		{
			b1.setEmailId(newemailId);
			customerRepo.save(b1);
			return b1;
		}
		return null;
	}
	
	public BankCustomer updateAddress(int custId,String newAddress) {
		BankCustomer b1=customerDetails(custId);
		System.out.println(b1);
		if(b1!=null)
		{
			b1.setAddress(newAddress);
			customerRepo.save(b1);
			return b1;
		}
		return null;
	}
	
	public BankCustomer updatePhoneNo(int custId,String newPhoneNo) {
		BankCustomer b1=customerDetails(custId);
		if(b1!=null)
		{
			b1.setPhoneno(newPhoneNo);
			customerRepo.save(b1);
			return b1;
		}
		return null;
	}
	
	@Transactional
	public BankCustomer addCustomer(BankCustomer customer)
	{
		MyUser newUser = new MyUser();
		newUser.setUsername(customer.getName());
		System.out.println(newUser.getUsername());
		newUser.setPassword(getPassword("customer567"));
		newUser.setActive(true);
		newUser.setRoles("ROLE_CUSTOMER");
		userRepo.save(newUser);
		return customerRepo.save(customer);
		
	}

	public Account addAccount(int custId,Account account) {
		BankCustomer b1=customerDetails(custId);
		if(b1!=null)
		{
			b1.setAccount(account);
			//customerRepo.save(b1);
			return accountRepo.save(account);
		}
		return null;
	}

	public MyUser forgotPassword(String username, String password) {
		MyUser b1=userDetails(username);
		if(b1!=null)
		{
			b1.setPassword(password);
			return userRepo.save(b1);
		}
		return null;
	}

	public MyUser updatePassword(String username, String oldpassword, String newpassword) {
		MyUser b1=userDetails(username);
		boolean c=b1.getPassword().equals(oldpassword);
		System.out.println(c);
		if(b1!=null && c)
		{
			b1.setPassword(newpassword);
			return userRepo.save(b1);
		}
		return null;
	}

	public void writeTransactionToCsv(Writer writer) {

        List<Transaction> transactions = transactionRepo.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Transaction transaction : transactions) {
                csvPrinter.printRecord(transaction.getTransId(),transaction.getAmount(), transaction.getDate(),transaction.getFromAccount(), transaction.getToAccount(), transaction.getTransactionType());
                System.out.println(transaction);
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }

}
	
}

