package com.example.bank.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Entity
@NoArgsConstructor(force = true)
@Table(name="TRANSACTIONS")
@JsonSerialize(using = TransactionSerializer.class)
public class Transaction {
	
	@Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "transactionId")
	private Long id;

	@Column(name = "amount")
	private final BigDecimal amount;
	
	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name="accountSourceNumber")
	private final Account accountSource;
	
	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name="accountDestinationNumber")
	private final Account accountDestination;
	
	
	
}
