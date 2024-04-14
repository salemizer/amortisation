package com.ldms.amortisation.Service;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldms.amortisation.Domain.LoanDetails;
import com.ldms.amortisation.Domain.Payment;
import com.ldms.amortisation.Domain.Schedule;
import com.ldms.amortisation.Domain.ScheduleSummary;
import com.ldms.amortisation.Repository.LoanDetailsRepository;
import com.ldms.amortisation.Repository.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LoanDetailsRepository loanDetailsRepo;
	
	@Autowired
	private ScheduleRepository scheduleRepo;
	
	public ScheduleServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	 
	 public Schedule createSchedule(Double cost, Double deposit, Integer noOfPayments, Double interestRate, Double balloon){
    	
//		 DecimalFormat decimalFormat = new DecimalFormat("#.00");
//		 
//		 DoubledecimalFormat.format(cost);
		 
	    ScheduleSummary summary= new ScheduleSummary();
	
		LoanDetails loanDetails= new LoanDetails(cost, deposit, interestRate, noOfPayments, balloon);
		
		double totalInterestDue=0; 
		double totalPaymentDue=0;
	
		 double P= cost - deposit;
		 double r= (interestRate/100)/ 12;
		double monthlyRepayment= calculateMonthlyRepaymentBalloon(P, noOfPayments,  r, balloon);
		summary.setMonthlyRepayment(monthlyRepayment);
		 
		 List<Payment> payments= new LinkedList<Payment>();
		 
		 for(int i=1; i <= noOfPayments; i++) {
			 
			 Double[] arr= calculatePaymentApportion(P, r, monthlyRepayment);
			 double remaining= calculateRemainingBalance(P, arr[0]);
			 payments.add(new Payment(i, monthlyRepayment, arr[0], arr[1], remaining));
			 P= remaining;
			 
			 totalInterestDue += arr[1];
			 totalPaymentDue += monthlyRepayment;
		 }
		 
		 Schedule schedule= new Schedule(payments);
		  summary.setTotalInterestDue(totalInterestDue);
		  summary.setTotalPaymentsDue(totalPaymentDue);
		
		schedule.setScheduleSummary(summary);
		summary.setSchedule(schedule);
		
		schedule.setLoanDetails(loanDetails);	
		loanDetails.setSchedule(schedule);
		
//		return loanDetailsRepo.saveLoanDetails(loanDetails);
		return scheduleRepo.saveSchedule(schedule);
    }
	 
		
	 
//	 (P - (B / ((1 + r) ^ n))) * (r / (1 - (1 + r) ^ -n))
  private Double calculateMonthlyRepaymentBalloon(Double P, Integer noOfPayments, double r, double balloon){
	   
	   return (P -(balloon / (Math.pow(1 + r, noOfPayments)))) * (r / (1- Math.pow(1 + r, -noOfPayments)));
}
  
//  P * ((r * (1 + r) ^ n) / ((1 + r) ^ n - 1))
  private Double calculateMonthlyRepaymentNoBalloon(Double P, Integer noOfPayments, double r){
	  
	   return P * ((r * Math.pow(1 + r, noOfPayments)) / (Math.pow(1 + r, noOfPayments)-1));
}  
		

  private Double[] calculatePaymentApportion(Double P, Double r, Double monthlyRepayment){
	  double interest= calculateInterest(P, r);
	  double principle= calculatePrinciple(monthlyRepayment, interest);
	  return new Double[] {principle, interest};
  }
	
	
 private Double calculateInterest(Double P, Double r) {
		return P * r;
	}
	
	private Double calculatePrinciple(Double P, Double interest ) {
		return P - interest;
	}
	
	private Double calculateRemainingBalance(Double P, Double principle) {
		return P - principle;
	}

	@Override
	public Schedule findSchedule(Long scheduleid) {
		// TODO Auto-generated method stub
		return scheduleRepo.findSchedule(scheduleid);
	}
	
	@Override
	public List<Schedule> listSchedule() {
		// TODO Auto-generated method stub
		return scheduleRepo.listSchedule();
	}

	@Override
	public List<Payment> findSchedulePayments(Long scheduleid) {
		// TODO Auto-generated method stub
		log.debug("scheduleid= " + scheduleid);
		return scheduleRepo.findSchedulePayments(scheduleid);
	}

	
}
