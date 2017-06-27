package com.company.report.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The entity class which holds data for the trade instructions sent by various
 * clients to JP Morgan.
 *
 * @author prejith.devarajan
 *
 */
public class Instruction {

	// A financial entity whose shares are to be bought or sold
	private final String entity;

	// The trade action (Buy or Sell)
	private final TradeTxn tradeTxn;

	// The foreign exchange rate with respect to USD that was agreed
	private final BigDecimal agreedFx;

	// Currency code for the trade
	private final String currency;

	// Date on which the instruction was sent to JP Morgan by various clients
	private final LocalDate instructionDate;

	// The date on which the client wished for the instruction to be settled with respect to Instruction Date
	private LocalDate settlementDate;

	// Number of shares to be bought or sold
	private final int units;

	// The price agreed for each unit in the trade
	private final BigDecimal pricePerUnit;

	// USD amount of a trade = Price per unit * Units * Agreed Fx
	private final BigDecimal tradeAmount;

	/**
	 * @param entity
	 * @param trade
	 * @param agreedFx
	 * @param currency
	 * @param instructionDate
	 * @param settlementDate
	 * @param units
	 * @param pricePerUnit
	 */
	public Instruction(String entity, TradeTxn trade, BigDecimal agreedFx,
			String currency, LocalDate instructionDate, LocalDate settlementDate,
			int units, BigDecimal pricePerUnit) {
		this.entity = entity;
		this.tradeTxn = trade;
		this.agreedFx = agreedFx;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
		this.tradeAmount = calculateTradeAmount();
	}

	/**
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @return the tradeTxn
	 */
	public TradeTxn getTradeTxn() {
		return tradeTxn;
	}

	/**
	 * @return the agreedFx
	 */
	public BigDecimal getAgreedFx() {
		return agreedFx;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @return the instructionDate
	 */
	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	/**
	 * @return the settlementDate
	 */
	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @return the units
	 */
	public int getUnits() {
		return units;
	}

	/**
	 * @return the pricePerUnit
	 */
	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	/**
	 *
	 * @return tradeAmount
	 */
	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	/**
	 * @param settlementDate the settlementDate to set
	 */
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	/**
	 * Calculates the trade amount in USD
	 *n
	 * @return tradeAmount
	 */
	private BigDecimal calculateTradeAmount() {
		return getPricePerUnit().multiply(BigDecimal.valueOf(getUnits()).multiply(getAgreedFx()));
	}
}
