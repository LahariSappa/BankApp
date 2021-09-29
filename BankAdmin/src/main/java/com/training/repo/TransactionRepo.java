package com.training.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.training.bean.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

	@Query(value = "SELECT * FROM TRANSACTION WHERE (DATE >= :startDate AND DATE <= :endDate) AND (from_account=:accountId AND to_account=:accountId)", nativeQuery = true)
    List<Transaction> getAllBetweenDates(@Param("accountId") int accountId,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
	
	@Query(value = "SELECT * FROM TRANSACTION WHERE from_account =:from_account", nativeQuery = true)
	List<Transaction> findByFromAccount(@Param("from_account") int from_account);

}
