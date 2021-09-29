package com.training.services;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.training.bean.Transaction;
import com.training.repo.TransactionRepo;

@Service
public class CsvExportService {
	private static final Logger log = LoggerFactory.getLogger(CsvExportService.class);

    private final TransactionRepo transactionRepo;

    public CsvExportService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
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
