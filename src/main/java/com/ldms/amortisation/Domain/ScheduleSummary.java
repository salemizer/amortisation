package com.ldms.amortisation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity(name = "schedule_summary")
public class ScheduleSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "summary_id")
	@JsonIgnore
	private Long summaryid;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Schedule schedule;

	@Column(name = "monthly_payment")
	private double monthlyRepayment;

	@Column(name = "total_Interest_due")
	private double totalInterestDue;

	@Column(name = "total_payments_due")
	private double totalPaymentsDue;

	public ScheduleSummary() {
	}

	public Long getSummaryid() {
		return summaryid;
	}

	public void setSummaryid(Long summaryid) {
		this.summaryid = summaryid;
	}

	public double getMonthlyRepayment() {
		return monthlyRepayment;
	}

	public void setMonthlyRepayment(double monthlyRepayment) {
		this.monthlyRepayment = monthlyRepayment;
	}

	public double getTotalInterestDue() {
		return totalInterestDue;
	}

	public void setTotalInterestDue(double totalInterestDue) {
		this.totalInterestDue = totalInterestDue;
	}

	public double getTotalPaymentsDue() {
		return totalPaymentsDue;
	}

	public void setTotalPaymentsDue(double totalPaymentsDue) {
		this.totalPaymentsDue = totalPaymentsDue;
	}

	public Schedule getSchedule() {
		return this.schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

}
