package com.training.bean;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateSearcher {
	
	private int accountId;
	private LocalDate startDate;
	private LocalDate endDate;

}

