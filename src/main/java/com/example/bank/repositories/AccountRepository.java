package com.example.bank.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.bank.model.Account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface AccountRepository extends JpaRepository<Account, String> {
	  Optional<Account> findByNumber(@Param("number") String number);
	  
	  @Override
	    @RestResource(exported = false)
	    public void delete(Account account);
	}
