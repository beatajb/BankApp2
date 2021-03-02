package com.example.bank;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.repositories.AccountRepository;
import com.example.bank.repositories.TransactionRepository;

@Configuration
public class PreloadData {

	private static final Logger log = LoggerFactory.getLogger(PreloadData.class);

	@Bean
	CommandLineRunner initDatabase(TransactionRepository repositoryTransaction, AccountRepository repositoryAccount) {

		repositoryTransaction.deleteAll();
		repositoryAccount.deleteAll();

		return args -> {
			Account accountDummy = new Account("000", "Dummy Dummy");
			Account accountDummy2 = new Account("002", "Dummy Dummy");
			Account accountDummy3 = new Account("003", "Dummy Dummy");
			Account account = new Account("345", "Brian Glad");
			Account account2 = new Account("456","Bilbo Baggins");
			Account account3 = new Account("567", "Glorial Gr8");
			
			log.info("Preloading " + repositoryAccount.save(account));
			log.info("Preloading " + repositoryAccount.save(account2));
			log.info("Preloading " + repositoryAccount.save(account3));
			log.info("Preloading " + repositoryAccount.save(accountDummy));
			log.info("Preloading " + repositoryAccount.save(accountDummy2));
			log.info("Preloading " + repositoryAccount.save(accountDummy3));
			
			Transaction transaction1 = new Transaction(null,new BigDecimal(350.40), accountDummy, account);
			Transaction transaction2 = new Transaction(null,new BigDecimal(350.40), accountDummy2, account2);
			Transaction transaction3 = new Transaction(null,new BigDecimal(350.40), accountDummy3, account3);
			
			log.info("Preloading " + repositoryTransaction.save(transaction1));
			log.info("Preloading " + repositoryTransaction.save(transaction2));
			log.info("Preloading " + repositoryTransaction.save(transaction3));
			
			

		};
	}
}