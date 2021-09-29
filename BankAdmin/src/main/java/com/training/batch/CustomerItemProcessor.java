package com.training.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.training.bean.CustomerRequests;



public class CustomerItemProcessor implements ItemProcessor<CustomerRequests, CustomerRequests> {

  private static final Logger log = LoggerFactory.getLogger(CustomerItemProcessor.class);


@Override
public CustomerRequests process(final CustomerRequests customer) throws Exception {
	log.info("Started processing the csv file..");
	final String email_id = customer.getEmail_id().toUpperCase();
    final String name = customer.getName().toUpperCase();
    final String address = customer.getAddress().toUpperCase();
    final String phoneno = customer.getPhoneno();

    final CustomerRequests transformedPerson = new CustomerRequests(email_id,name, address,phoneno);

    log.info("Converting (" + customer + ") into (" + transformedPerson + ")");

    return transformedPerson;
}

}