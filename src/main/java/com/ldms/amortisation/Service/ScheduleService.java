package com.ldms.amortisation.service;

import java.util.List;
import com.ldms.amortisation.domain.Payment;
import com.ldms.amortisation.domain.Schedule;

public interface ScheduleService {

	Schedule createSchedule(Double cost, Double deposit, Integer noOfPayments, Double interestRate, Double balloon);

	List<Schedule> listSchedule();

	List<Payment> findSchedulePayments(Long scheduleid);

}
