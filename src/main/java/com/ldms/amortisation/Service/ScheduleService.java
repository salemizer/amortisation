package com.ldms.amortisation.service;

import java.util.List;

import com.ldms.amortisation.domain.LoanDetails;
import com.ldms.amortisation.domain.Payment;
import com.ldms.amortisation.domain.Schedule;

public interface ScheduleService {

	Schedule createSchedule(LoanDetails loanDetails);

	List<Schedule> listSchedule();

	List<Payment> findSchedulePayments(Long scheduleid);

}
