package com.training.bean;

import java.util.Date;

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
@Table(name="Transaction")

public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transId;
	
	private int fromAccount;
	private int toAccount;
	private Date date; 
	private String transactionType;
	private double amount;
	

}
