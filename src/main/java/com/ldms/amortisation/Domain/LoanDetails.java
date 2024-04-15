package com.ldms.amortisation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import com.ldms.amortisation.Domain.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

@Entity(name = "loan_details")
public class LoanDetails {

	@Column(name = "details_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long detailsid;

    @PositiveOrZero
	private Double cost;
    
    @PositiveOrZero
	private Double deposit;
    
    @PositiveOrZero
	@Column(name = "interest_rate")
	private Double interestRate;
    
    @PositiveOrZero
    @Min(value = 1)
	@Column(name = "no_of_payments")
	private Integer noOfPayments;
    
    @PositiveOrZero
    @Min(value = 0)
	private Double balloon;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Schedule schedule;

	public LoanDetails() {

	}

	public LoanDetails(Double cost, Double deposit, Double interestRate, Integer noOfPayments, Double balloon) {
		super();
		this.cost = cost;
		this.deposit = deposit;
		this.interestRate = interestRate;
		this.noOfPayments = noOfPayments;
		this.balloon = balloon;
	}

	public Long getDetailsid() {
		return detailsid;
	}

	public void setDetailsid(Long detailsid) {
		this.detailsid = detailsid;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Integer getNoOfPayments() {
		return noOfPayments;
	}

	public void setNoOfPayments(Integer noOfPayments) {
		this.noOfPayments = noOfPayments;
	}

	public Double getBalloon() {
		return balloon;
	}

	public void setBalloon(Double balloon) {
		this.balloon = balloon;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

}
