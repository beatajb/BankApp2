package com.example.bank.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.bank.repositories.TransactionRepository;

@Component
public class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>> {

  @Override
  public EntityModel<Transaction> toModel(Transaction item) {
	  

    return EntityModel.of(item,
        linkTo(methodOn(TransactionRepository.class).findById(item.getId())).withSelfRel(),
        linkTo(methodOn(TransactionRepository.class).findAll()).withRel("transactions"));
  }
  }