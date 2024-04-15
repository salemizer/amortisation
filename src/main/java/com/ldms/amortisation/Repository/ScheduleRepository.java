package com.ldms.amortisation.repository;

import java.util.List;
import com.ldms.amortisation.domain.Payment;
import com.ldms.amortisation.domain.Schedule;

public interface ScheduleRepository {

	Schedule saveSchedule(Schedule schedule);

	List<Schedule> listSchedule();

	List<Payment> findSchedulePayments(Long scheduleid);
}
