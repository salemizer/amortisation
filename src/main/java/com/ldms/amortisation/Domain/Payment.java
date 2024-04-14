package com.ldms.amortisation.Domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long paymentid;

	private Integer period;
	private double payment;
	private double principle;
	private double interest;
	private double balanace;

	public Payment() {
	}

	public Payment(Integer period, double payment, double principle, double interest, double balanace) {
		super();
		this.period = period;
		this.payment = payment;
		this.principle = principle;
		this.interest = interest;
		this.balanace = balanace;
	}

	public Long getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(Long paymentid) {
		this.paymentid = paymentid;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public double getPrinciple() {
		return principle;
	}

	public void setPrinciple(double principle) {
		this.principle = principle;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public double getBalanace() {
		return balanace;
	}

	public void setBalanace(double balanace) {
		this.balanace = balanace;
	}
}