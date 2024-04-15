package com.ldms.amortisation.service;

import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ldms.amortisation.Utility;
import com.ldms.amortisation.domain.LoanDetails;
import com.ldms.amortisation.domain.Payment;
import com.ldms.amortisation.domain.Schedule;
import com.ldms.amortisation.domain.ScheduleSummary;
import com.ldms.amortisation.repository.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ScheduleRepository scheduleRepo;

	public ScheduleServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public Schedule createSchedule(LoanDetails loanDetails) {

		double totalInterestDue = 0;
		double totalPaymentDue = 0;
		double P = loanDetails.getCost() - loanDetails.getDeposit();
		double r = (loanDetails.getInterestRate() / 100) / 12;

		double monthlyRepayment = calculateMonthlyRepayment(P, loanDetails.getNoOfPayments(), r, loanDetails.getBalloon());

		Double[] data = Utility.twoDecimalPlaces(loanDetails.getCost(), loanDetails.getDeposit(), loanDetails.getInterestRate(), loanDetails.getBalloon());
		loanDetails = new LoanDetails(data[0], data[1], data[2], loanDetails.getNoOfPayments(), data[3]);

		List<Payment> payments = new LinkedList<Payment>();

		for (int i = 1; i <= loanDetails.getNoOfPayments(); i++) {

			Double[] arr = calculatePaymentApportion(P, r, monthlyRepayment);
			double remaining = calculateRemainingBalance(P, arr[0]);

			Double[] values = Utility.twoDecimalPlaces(monthlyRepayment, arr[0], arr[1], remaining);
			payments.add(new Payment(i, values[0], values[1], values[2], values[3]));

			P = remaining;
			totalInterestDue += arr[1];
			totalPaymentDue += monthlyRepayment;
		}

		Schedule schedule = new Schedule(payments);

		ScheduleSummary summary = new ScheduleSummary();
		summary.setMonthlyRepayment(Utility.twoDecimalPlaces(monthlyRepayment));
		summary.setTotalInterestDue(Utility.twoDecimalPlaces(totalInterestDue));
		summary.setTotalPaymentsDue(Utility.twoDecimalPlaces(totalPaymentDue));

		schedule.setScheduleSummary(summary);
		summary.setSchedule(schedule);

		schedule.setLoanDetails(loanDetails);
		loanDetails.setSchedule(schedule);

		return scheduleRepo.saveSchedule(schedule);
	}

	private Double calculateMonthlyRepayment(Double P, Integer noOfPayments, double r, double balloon) {

		Double value = 0.0;
		if (balloon > 0) {
//			 (P - (B / ((1 + r) ^ n))) * (r / (1 - (1 + r) ^ -n))
			value = (P - (balloon / (Math.pow(1 + r, noOfPayments)))) * (r / (1 - Math.pow(1 + r, -noOfPayments)));
		} else {
			// P * ((r * (1 + r) ^ n) / ((1 + r) ^ n - 1))
			value = P * ((r * Math.pow(1 + r, noOfPayments)) / (Math.pow(1 + r, noOfPayments) - 1));
		}
		return value;
	}

	private Double[] calculatePaymentApportion(Double P, Double r, Double monthlyRepayment) {

		double interest = calculateInterest(P, r);
		double principle = calculatePrinciple(monthlyRepayment, interest);

		return new Double[] { principle, interest };
	}

	private Double calculateInterest(Double P, Double r) {
		return P * r;
	}

	private Double calculatePrinciple(Double P, Double interest) {
		return P - interest;
	}

	private Double calculateRemainingBalance(Double P, Double principle) {

		return P - principle;
	}

	@Override
	public List<Schedule> listSchedule() {
		// TODO Auto-generated method stub
		return scheduleRepo.listSchedule();
	}

	@Override
	public List<Payment> findSchedulePayments(Long scheduleid) {
		// TODO Auto-generated method stub
		return scheduleRepo.findSchedulePayments(scheduleid);
	}

}
