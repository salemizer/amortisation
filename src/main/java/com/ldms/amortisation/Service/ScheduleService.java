package com.ldms.amortisation.Service;

import com.ldms.amortisation.Domain.LoanDetails;
import com.ldms.amortisation.Domain.Payment;
import com.ldms.amortisation.Domain.Schedule;
import java.util.List;

public interface ScheduleService {

	Schedule createSchedule(Double cost, Double deposit, Integer noOfPayments, Double interestRate, Double balloon);
	Schedule findSchedule(Long scheduleid);
	List<Schedule> listSchedule();
	List<Payment> findSchedulePayments(Long scheduleid);
	
}
