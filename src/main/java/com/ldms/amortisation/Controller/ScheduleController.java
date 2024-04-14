package com.ldms.amortisation.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ldms.amortisation.Domain.LoanDetails;
import com.ldms.amortisation.Domain.Payment;
import com.ldms.amortisation.Domain.Schedule;
import com.ldms.amortisation.Service.ScheduleService;

import jakarta.validation.Valid;

@RequestMapping("schedules")
@RestController
public class ScheduleController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	ScheduleService scheduleService;

	@PostMapping("/create")
	public ResponseEntity<Schedule> createSchedule(@RequestParam @Valid Double cost, @Valid Double deposit,
			@Valid Double interestRate, @Valid Integer noOfPayments, @Valid Double balloon) {

		Schedule details = scheduleService.createSchedule(cost, deposit, noOfPayments, interestRate, balloon);

		return new ResponseEntity<Schedule>(details, HttpStatus.CREATED);
	}

	@GetMapping("/{scheduleid}")
	public ResponseEntity<Schedule> findSchedule(@PathVariable Long scheduleid) {

		Schedule schedule = scheduleService.findSchedule(scheduleid);

		if (schedule == null)
			throw new RuntimeException("Exception saving the Loan details!!!");

		return new ResponseEntity<Schedule>(schedule, HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<Schedule>> listSchedule() {

		List<Schedule> schedules = scheduleService.listSchedule();

		if (schedules == null)
			throw new RuntimeException("Exception saving the Loan details!!!");

		return new ResponseEntity<List<Schedule>>(schedules, HttpStatus.OK);
	}

	@GetMapping("/amortisation/{scheduleid}")
	public ResponseEntity<List<Payment>> findSchedulePayments(@PathVariable Long scheduleid) {

		List<Payment> payments = scheduleService.findSchedulePayments(scheduleid);

		if (payments == null)
			throw new RuntimeException("Exception saving the Loan details!!!");

		return new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
	}
}
