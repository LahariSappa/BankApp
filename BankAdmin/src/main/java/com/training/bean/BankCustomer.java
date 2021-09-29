package com.training.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString

@Entity
@Table(name="BankCustomer")
public class BankCustomer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int custId;
	private String Name;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="accountId")
	private Account account;
	
	private String emailId;
	private String address;
	private String phoneno;

}
