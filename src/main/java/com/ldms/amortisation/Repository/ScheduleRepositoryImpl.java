package com.ldms.amortisation.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ldms.amortisation.domain.Payment;
import com.ldms.amortisation.domain.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

	@PersistenceContext
	EntityManager manager;

	@Override
	public Schedule saveSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		return manager.merge(schedule);
	}

	@Override
	public List<Schedule> listSchedule() {
		// TODO Auto-generated method stub
		return manager.createQuery("select s from amortisation_schedule s").getResultList();
	}

	@Override
	public List<Payment> findSchedulePayments(Long scheduleid) {
		// TODO Auto-generated method stub
		Query query = manager.createNativeQuery(
				"select p.* from payment p inner join AMORTISATION_SCHEDULE  a on p.schedule_id = a.schedule_id  and a.schedule_id= :scheduleid", Payment.class);
		query.setParameter("scheduleid", scheduleid);
		return query.getResultList();
	}
}
