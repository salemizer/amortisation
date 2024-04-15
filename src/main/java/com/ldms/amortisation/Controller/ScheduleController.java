package com.ldms.amortisation.controller;

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

import com.ldms.amortisation.domain.LoanDetails;
import com.ldms.amortisation.domain.Payment;
import com.ldms.amortisation.domain.Schedule;
import com.ldms.amortisation.service.ScheduleService;

import jakarta.validation.Valid;

@RequestMapping("/api")
@RestController
public class ScheduleController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	ScheduleService scheduleService;

	@PostMapping("/v1/schedules")
	public ResponseEntity<Schedule> createSchedule(@RequestParam @Valid Double cost, @Valid Double deposit,
			@Valid Double interestRate, @Valid Integer noOfPayments, @Valid Double balloon) {

		Schedule details = scheduleService.createSchedule(cost, deposit, noOfPayments, interestRate, balloon);

		return new ResponseEntity<Schedule>(details, HttpStatus.CREATED);
	}


	@GetMapping("/v1/schedules")
	public ResponseEntity<List<Schedule>> listSchedules() {

		List<Schedule> schedules = scheduleService.listSchedule();

		if (schedules == null)
			throw new RuntimeException("Exception saving the Loan details!!!");

		return new ResponseEntity<List<Schedule>>(schedules, HttpStatus.OK);
	}

	@GetMapping("/v1/schedules/{scheduleid}")
	public ResponseEntity<List<Payment>> findSchedulePayments(@PathVariable Long scheduleid) {

		List<Payment> payments = scheduleService.findSchedulePayments(scheduleid);

		if (payments == null)
			throw new RuntimeException("Exception saving the Loan details!!!");

		return new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
	}
}
