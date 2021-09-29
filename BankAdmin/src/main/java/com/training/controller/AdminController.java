package com.training.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.util.JwtUtil;
import com.training.bean.Account;
import com.training.bean.AuthRequest;
import com.training.bean.BankCustomer;
import com.training.bean.DateSearcher;
import com.training.bean.MyUser;
import com.training.bean.Transaction;
import com.training.repo.TransactionRepo;
import com.training.services.AdminService;
import com.training.services.CustomerServices;

@RestController
@RequestMapping("/")

public class AdminController {

	@GetMapping("/admin/hello")
	public String sayHello()
	{
		return "Hello admin";
	}
	@GetMapping("/manager/manager")
	public String sayManager()
	{
		return "Hello manager";
	}
	@GetMapping("/customer/hello")
	public String sayHello3()
	{
		return "Hello customer";
	}

	@GetMapping("/user/hello")
	public String sayHello1()
	{
		return "Hello user";
	}

	@Autowired
	private AdminService adminService;
	@Autowired
	private CustomerServices customerServices;
	@Autowired
	private TransactionRepo transactionRepo;

	@GetMapping("/customer/custId/{custId}")
	public BankCustomer customerDetails(@PathVariable("custId") int custId)
	{
		return customerServices.customerDetails(custId);
	}

	@GetMapping("/account/accountId/{accountId}")
	public Account amount(@PathVariable("accountId") int accountId)
	{
		return customerServices.amount(accountId);
	}

	@DeleteMapping("/admin/delaccount/accountId/{accountId}")
	public Account delAccount(@PathVariable("accountId") int accountId)
	{
		return adminService.delAccount(accountId);
	}

	@PostMapping("/admin/updatecustomer/custId/{custId}/emailId/{emailId}")
	public BankCustomer updateMail(@PathVariable("custId") int custId,@PathVariable("emailId") String emailId)
	{
		return adminService.updateMail(custId,emailId);
	}

	@PostMapping("/admin/updatecustomer/custId/{custId}/address/{address}")
	public BankCustomer updateAddress(@PathVariable("custId") int custId,@PathVariable("address") String address)
	{
		return adminService.updateAddress(custId,address);
	}

	@PostMapping("/admin/updatecustomer/custId/{custId}/phoneno/{phoneno}")
	public BankCustomer updatePhoneNo(@PathVariable("custId") int custId,@PathVariable("phoneno") String phoneno)
	{
		return adminService.updatePhoneNo(custId,phoneno);
	}

	@PostMapping("/admin/addcustomer")
	public BankCustomer addCustomer(@RequestBody BankCustomer customer)
	{
		return adminService.addCustomer(customer);
	}

	@PostMapping("/customer/addtransaction")
	public Transaction addTransaction(@RequestBody Transaction transaction)
	{
		return customerServices.addTransaction(transaction);
	}

	@PostMapping("/admin/custId/{custId}/addaccount")
	public Account addAccount(@PathVariable ("custId") int custId,@RequestBody Account account)
	{
		return adminService.addAccount(custId,account);
	}

	@PostMapping("/customer/accountId/{accountId}/find/date-between")
	public List<Transaction> findtransaction(@PathVariable("accountId") int accountId,@RequestBody DateSearcher searcherDto) {

		LocalDateTime start = LocalDateTime.of(LocalDate.from(searcherDto.getStartDate()),LocalTime.of(0, 0, 0));
		LocalDateTime end = LocalDateTime.of(LocalDate.from(searcherDto.getEndDate()), LocalTime.of(23, 59, 59));

		List<Transaction> transactionList =transactionRepo.getAllBetweenDates(accountId,start, end);
		return transactionList;
	}

	@PostMapping("/customer/username/{username}/forgot/{password}")
	public MyUser forgotPassword(@PathVariable ("username") String username,@PathVariable ("password") String password)
	{
		return adminService.forgotPassword(username,password);
	}

	@PostMapping("/customer/username/{username}/old/{oldpassword}/update/{newpassword}")
	public MyUser updatePassword(@PathVariable ("username") String username,@PathVariable ("oldpassword") String oldpassword,@PathVariable ("newpassword") String newpassword)
	{
		return adminService.updatePassword(username,oldpassword,newpassword);
	}

	@GetMapping("/manager/customerstransaction/{from_account}")
	public List<Transaction> findTransaction (@PathVariable int from_account){
		List<Transaction> transactionList =transactionRepo.findByFromAccount(from_account);
		return transactionList;
	}

	@GetMapping("/manager/accountdetails/{accountId}")
	public Account viewAccount(@PathVariable("accountId") int accountId)
	{
		return customerServices.viewAccount(accountId);
	}

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception { //from json input
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
					);
		} catch (Exception ex) {
			throw new Exception("invalid username/password");
		}

		return jwtUtil.generateToken(authRequest.getUsername());
	}

}

