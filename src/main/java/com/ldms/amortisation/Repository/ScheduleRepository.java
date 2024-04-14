package com.ldms.amortisation.Repository;

import java.util.List;

import com.ldms.amortisation.Domain.LoanDetails;
import com.ldms.amortisation.Domain.Payment;
import com.ldms.amortisation.Domain.Schedule;

public interface ScheduleRepository {

	Schedule findSchedule(Long scheduleid);
	Schedule saveSchedule(Schedule schedule);
	List<Schedule> listSchedule();
	List<Payment> findSchedulePayments(Long scheduleid);
}
