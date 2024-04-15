package com.ldms.amortisation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import javax.net.ssl.SSLEngineResult.Status;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.ldms.amortisation.domain.LoanDetails;
import com.ldms.amortisation.domain.Payment;
import com.ldms.amortisation.domain.Schedule;
import com.ldms.amortisation.domain.ScheduleSummary;
import com.ldms.amortisation.service.ScheduleService;

import org.springframework.http.MediaType;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ScheduleService scheduleService;

	@Test
	public void createSchedule_Basic() throws Exception {

		LoanDetails loanDetails = new LoanDetails();
		loanDetails.setDetailsid(new Long(1));
		loanDetails.setCost(new Double(25000.0));
		loanDetails.setDeposit(new Double(5000.0));
		loanDetails.setInterestRate(new Double(7.5));
		loanDetails.setNoOfPayments(new Integer(12));
		loanDetails.setBalloon(new Double(0.0));

		ScheduleSummary summary = new ScheduleSummary();
		summary.setMonthlyRepayment(new Double(1735.15));
		summary.setTotalInterestDue(821.78);
		summary.setTotalPaymentsDue(20821.78);

		Schedule schedule = new Schedule();
		schedule.setScheduleid(new Long(1));
		schedule.setLoanDetails(loanDetails);
		schedule.setScheduleSummary(summary);

		when(scheduleService.createSchedule(new Double(25000), new Double(5000), new Integer(12), new Double(7.5),
				new Double(0))).thenReturn(schedule);

		RequestBuilder request = MockMvcRequestBuilders
				.post("/api/v1/schedules?cost=25000&deposit=5000&interestRate=7.5&noOfPayments=12&balloon=0")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isCreated()).andExpect(content().json(
				"{loanDetails: {detailsid: 1, cost: 25000.0, deposit: 5000.0, interestRate: 7.5, noOfPayments: 12, balloon: 0.0}, scheduleSummary: {monthlyRepayment: 1735.15, totalInterestDue: 821.78, totalPaymentsDue: 20821.78}}"))
				.andReturn();
	}

	@Test
	public void listSchedules_Basic() throws Exception {

		// details with balloon set
		LoanDetails loanDetails1 = new LoanDetails();
		loanDetails1.setDetailsid(new Long(1));
		loanDetails1.setCost(new Double(25000.0));
		loanDetails1.setDeposit(new Double(5000.0));
		loanDetails1.setInterestRate(new Double(7.5));
		loanDetails1.setNoOfPayments(new Integer(12));
		loanDetails1.setBalloon(new Double(10000.0));

		ScheduleSummary summary1 = new ScheduleSummary();
		summary1.setMonthlyRepayment(new Double(930.07));
		summary1.setTotalInterestDue(1160.89);
		summary1.setTotalPaymentsDue(11160.89);

		Schedule schedule1 = new Schedule();
		schedule1.setLoanDetails(loanDetails1);
		schedule1.setScheduleSummary(summary1);

		// // details without balloon
		LoanDetails loanDetails2 = new LoanDetails();
		loanDetails2.setDetailsid(new Long(2));
		loanDetails2.setCost(new Double(25000.0));
		loanDetails2.setDeposit(new Double(5000.0));
		loanDetails2.setInterestRate(new Double(7.5));
		loanDetails2.setNoOfPayments(new Integer(12));
		loanDetails2.setBalloon(new Double(0.0));

		ScheduleSummary summary2 = new ScheduleSummary();
		summary2.setMonthlyRepayment(new Double(1735.15));
		summary2.setTotalInterestDue(821.78);
		summary2.setTotalPaymentsDue(20821.78);

		Schedule schedule2 = new Schedule();
		schedule2.setLoanDetails(loanDetails2);
		schedule2.setScheduleSummary(summary2);

		List<Schedule> list = new ArrayList<Schedule>();
		list.add(schedule1);
		list.add(schedule2);

		when(scheduleService.listSchedule()).thenReturn(list);

		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/schedules").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(
				"[{loanDetails: {detailsid: 1, cost: 25000.0, deposit: 5000.0, interestRate: 7.5, noOfPayments: 12, balloon: 10000.0}, scheduleSummary: {monthlyRepayment: 930.07, totalInterestDue: 1160.89, totalPaymentsDue: 11160.89}}, {loanDetails: {detailsid: 2, cost: 25000.0, deposit: 5000.0, interestRate: 7.5, noOfPayments: 12, balloon: 0.0}, scheduleSummary: {monthlyRepayment: 1735.15, totalInterestDue: 821.78, totalPaymentsDue: 20821.78}}]"))
				.andReturn();
	}

	// find schedule payments returns 404
	@Test
	public void findSchedulePayments_NotFound() throws Exception {

		List<Payment> list = new ArrayList<Payment>();

		when(scheduleService.findSchedulePayments(new Long(1))).thenReturn(list);

		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/schedules/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void findSchedulePayments_Basic() throws Exception {

		Payment payment1 = new Payment();
		payment1.setPeriod(1);
		payment1.setPayment(10093.85);
		payment1.setPrinciple(9968.85);
		payment1.setInterest(125.0);
		payment1.setBalanace(10031.15);

		Payment payment2 = new Payment();
		payment2.setPeriod(2);
		payment2.setPayment(10093.85);
		payment2.setPrinciple(10031.15);
		payment2.setInterest(62.69);
		payment2.setBalanace(0.0);

		List<Payment> list = new ArrayList<Payment>();
		list.add(payment1);
		list.add(payment2);

		when(scheduleService.findSchedulePayments(new Long(1))).thenReturn(list);

		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/schedules/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(
				"[{period: 1, payment: 10093.85, principle: 9968.85, interest: 125.0, balanace: 10031.15},{period: 2, payment: 10093.85, principle: 10031.15, interest: 62.69, balanace: 0.0}]"))
				.andReturn();
	}

}
