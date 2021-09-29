package com.training.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name="CustomerRequests")
public class CustomerRequests {
	
	@Id
	@Column(length = 100)
	private String email_id;
	private String name;
	private String address;
	private String phoneno;
	

}
