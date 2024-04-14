package com.ldms.amortisation.Repository;

import com.ldms.amortisation.Domain.LoanDetails;

public interface LoanDetailsRepository {

	LoanDetails saveLoanDetails(LoanDetails loanDetails);
	
}
