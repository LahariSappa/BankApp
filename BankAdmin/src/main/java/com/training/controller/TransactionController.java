package com.training.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.services.CsvExportService;

@RestController
@RequestMapping("/")
public class TransactionController {

	    private final CsvExportService csvExportService;

	    public TransactionController(CsvExportService csvExportService) {
	        this.csvExportService = csvExportService;
	    }

	    @RequestMapping(path = "/customer/transactions")
	    public void getAllTransactionsInCsv(HttpServletResponse servletResponse) throws IOException {
	        servletResponse.setContentType("text/csv");
	        servletResponse.addHeader("Content-Disposition","attachment; filename=\"transactions.csv\"");
	        csvExportService.writeTransactionToCsv(servletResponse.getWriter());
	    }
	    
	    @RequestMapping(path = "/customer/transactions/dates")
	    public void getTransactionsInCsv(HttpServletResponse servletResponse) throws IOException {
	        servletResponse.setContentType("text/csv");
	        servletResponse.addHeader("Content-Disposition","attachment; filename=\"transactions.csv\"");
	        csvExportService.writeTransactionToCsv(servletResponse.getWriter());
	    }

	}
