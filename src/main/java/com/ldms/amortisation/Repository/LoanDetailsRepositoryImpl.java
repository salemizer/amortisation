package com.ldms.amortisation.repository;

import org.springframework.stereotype.Repository;

import com.ldms.amortisation.domain.LoanDetails;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class LoanDetailsRepositoryImpl implements LoanDetailsRepository  {

	@PersistenceContext
	EntityManager manager;


	@Override
	public LoanDetails saveLoanDetails(LoanDetails loanDetails) {
		// TODO Auto-generated method stub
		return manager.merge(loanDetails) ;
	}


}
