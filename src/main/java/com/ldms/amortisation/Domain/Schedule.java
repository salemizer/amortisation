package com.ldms.amortisation.Domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name = "amortisation_schedule")
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id")
	private Long scheduleid;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
	private List<Payment> payments;

//	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private LoanDetails loanDetails;

//	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private ScheduleSummary summary;

	public Schedule() {
	}

	public Schedule(List<Payment> payments) {
		super();
		this.payments = payments;
	}

	public Long getScheduleid() {
		return this.scheduleid;
	}

	public void setScheduleid(Long scheduleid) {
		this.scheduleid = scheduleid;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public LoanDetails getLoanDetails() {
		return loanDetails;
	}

	public void setLoanDetails(LoanDetails loanDetails) {
		this.loanDetails = loanDetails;
	}

	public ScheduleSummary getScheduleSummary() {
		return this.summary;
	}

	public void setScheduleSummary(ScheduleSummary summary) {
		this.summary = summary;
	}
}
