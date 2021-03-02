package com.example.bank.controllers;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.bank.exceptions.AccountDoesNotExist;
import com.example.bank.exceptions.NotEnoughFundsException;
import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionModelAssembler;
import com.example.bank.repositories.AccountRepository;
import com.example.bank.repositories.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@BasePathAwareController
@RequiredArgsConstructor
public class TransactionController {
	@Autowired
	private final TransactionRepository transactionRepository;
	@Autowired
	private final AccountRepository accountRepository;
	
	@RequestMapping(value = "/requestTransactions", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/hal+json"})
	@ResponseBody
	public ResponseEntity<?> newTransaction(@Valid @RequestBody Transaction newTransaction) {    	
    	
		Optional<Account> chargedAccount = accountRepository.findById(newTransaction.getAccountSource().getNumber());
		Optional<Account> destinationAccount = accountRepository.findById(newTransaction.getAccountDestination().getNumber());
		log.info(chargedAccount.get().getNumber()+" "+chargedAccount.get().getOwnerFullName()+" "+chargedAccount.get().getBalance());
				if(chargedAccount.isPresent()&&destinationAccount.isPresent()) {
    	if(!hasEnoughResources(chargedAccount.get(), newTransaction.getAmount())) {
			throw new NotEnoughFundsException(chargedAccount.get().getBalance().toString());
		}	
    	Transaction transaction1 = new Transaction(null, newTransaction.getAmount(), chargedAccount.get(), destinationAccount.get());
    	Transaction saved = transactionRepository.save(transaction1);
    	
    	return new ResponseEntity<>(saved, HttpStatus.OK);}
		throw new AccountDoesNotExist(newTransaction.getAccountSource().getNumber(), newTransaction.getAccountDestination().getNumber());
	}

	private boolean hasEnoughResources(Account chargedAccount, BigDecimal amount) {
		if(chargedAccount.getBalance().compareTo(amount)>=0) {
			return true;
		}
		return false;
	}
}
