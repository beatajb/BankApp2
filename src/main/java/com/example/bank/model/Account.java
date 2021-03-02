package com.example.bank.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.authentication.AccountStatusException;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Account {
	@Getter
	@NonNull
	@NotEmpty
	@Id
	@Column(name = "accountNumber")
	private final String number;
	
	@Column(name = "ownerName")
	@NonNull
	@NotEmpty
	@Getter
	private final String ownerFullName;
	
	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.EAGER, mappedBy="accountSource" )
	private List<Transaction> accountWithdrawals;
	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.EAGER, mappedBy="accountDestination" )
	private List<Transaction> accountDeposits;
	
	public BigDecimal getBalance() {
		List<BigDecimal> balanceHistory = new ArrayList<>();
		for(Transaction t:accountDeposits) {
				balanceHistory.add(t.getAmount());}
		for(Transaction t:accountWithdrawals) {
				balanceHistory.add(t.getAmount().negate());
		}
		return balanceHistory.stream().reduce(BigDecimal.ZERO, BigDecimal::add);		
	}
	
	public void addTransaction(Transaction t) {
		if(t.getAccountDestination().number.equals(this.number)) {
			if (accountDeposits==null) {
				accountDeposits = new ArrayList<>();
			}
			accountDeposits.add(t);
		}
		if(t.getAccountSource().number.equals(this.number)) {
			if (accountWithdrawals==null) {
				accountWithdrawals = new ArrayList<>();
			}
			accountWithdrawals.add(t);
		}
	}

}
