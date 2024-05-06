package com.swiftbuy.admin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DiscountAndOffers {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;
	 private String bank_offers;
	 private String cashback_offers;
	 private String no_cost_emi;
	public Long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}
	public String getBank_offers() {
		return bank_offers;
	}
	public void setBank_offers(String bank_offers) {
		this.bank_offers = bank_offers;
	}
	public String getCashback_offers() {
		return cashback_offers;
	}
	public void setCashback_offers(String cashback_offers) {
		this.cashback_offers = cashback_offers;
	}
	public String getNo_cost_emi() {
		return no_cost_emi;
	}
	public void setNo_cost_emi(String no_cost_emi) {
		this.no_cost_emi = no_cost_emi;
	}
	

	

}
