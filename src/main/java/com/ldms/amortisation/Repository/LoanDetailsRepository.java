package com.ldms.amortisation.repository;

import com.ldms.amortisation.domain.LoanDetails;

public interface LoanDetailsRepository {

	LoanDetails saveLoanDetails(LoanDetails loanDetails);
	
}
