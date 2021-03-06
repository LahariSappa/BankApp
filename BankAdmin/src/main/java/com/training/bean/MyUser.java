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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data

@Entity
@Table(name="userDetails")
public class MyUser {

	@Id
	@Column(name="username",length=100)
	private String username;
	private String password;
	private boolean active;
	private String roles;

}

