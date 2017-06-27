package com.company.report.handler.impl;

import java.math.BigDecimal;

import com.company.report.entity.TradeReport;
import com.company.report.handler.IReport;
import com.company.report.utils.DailyTradeReportingConstants;

/**
 * The report class which implements the logic for printing the trade amount
 * reports. The class helps in calculating the sum of trade amounts in USD for
 * all trade amount reports. The class handles the incoming and outgoing amount
 * reports.
 *
 * It is assumed that the print logic for incoming amount report and outgoing
 * amount report is same.
 *
 * @author prejith.devarajan
 *
 */
public class TradeAmountReport implements IReport {
	// The total amount for trade amount reports
	private BigDecimal amount = new BigDecimal(0);

	// The trade report enum - to identify various trade amount reports
	private final TradeReport tradeReport;

	TradeAmountReport(TradeReport tradeReport) {
		this.tradeReport = tradeReport;
	}

	/**
	 * Constructs the text format of the report to be printed
	 *
	 * @return
	 * 			the <code>StringBuilder</code> with the text format of report
	 */
	@Override
	public StringBuilder printReport() {
		StringBuilder printBuilder = new StringBuilder();

		printBuilder.append(this.tradeReport)
					.append(":      $")
					.append(getAmount())
					.append("\n");

		return printBuilder;
	}

	/**
	 * Sums up all the incoming trade amount
	 *
	 * @param amount
	 */
	void addAmount(final BigDecimal amount) {
		this.amount = this.amount.add(amount);
	}

	/**
	 * The method returns the total trade amount for incoming or outgoing trade
	 * instruction
	 *
	 * @return total incoming trade amount
	 */
	public BigDecimal getAmount() {
		return this.amount.setScale(DailyTradeReportingConstants.CURRENCY_DECIMAL_SCALE_VALUE);
	}
}
