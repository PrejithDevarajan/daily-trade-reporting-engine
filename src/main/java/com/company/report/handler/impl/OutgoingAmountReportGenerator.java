package com.company.report.handler.impl;

import java.util.Map;

import com.company.report.entity.Instruction;
import com.company.report.entity.TradeReport;
import com.company.report.entity.TradeTxn;
import com.company.report.handler.IReport;
import com.company.report.handler.IReportGenerator;

/**
 * The implementation class for the outgoing amount report. The class defines the
 * logic to generate the outgoing amount report.
 *
 * @author prejith.devarajan
 *
 */
public class OutgoingAmountReportGenerator implements IReportGenerator {

	/**
	 * Defines the report generation logic. For the
	 * <code>TradeAmountReport</code> sum up the outgoing trade amount for each
	 * instruction.
	 *
	 * The method puts a new report type to the map if a report for the specific
	 * type is not already available. It is also considered that a report for
	 * all report types will be created even if data for that specific report
	 * type is available in the instructions. Means, if there is no outgoing
	 * trade available in the instructions, the outgoing trade amount report
	 * will still display $0.000
	 *
	 *
	 * @param tradeReport
	 *            The map which holds the report data, each entry representing
	 *            each report type
	 * @param instruction
	 *            The trade instruction
	 *
	 */
	@Override
	public void generateReport(Map<TradeReport, IReport> tradeReport, final Instruction instruction) {

		TradeAmountReport report = (TradeAmountReport) tradeReport.get(TradeReport.DAILY_OUTGOING_AMOUNT_REPORT);
		/*
		 * Create new report instance if no report for the settlement date is
		 * already defined. This creates an entry even if the whole instructions
		 * does not contain an incoming trade instruction.
		 */
		if(report == null){
			report = new TradeAmountReport(TradeReport.DAILY_OUTGOING_AMOUNT_REPORT);
			tradeReport.put(TradeReport.DAILY_OUTGOING_AMOUNT_REPORT, report);
		}

		// Sum the trade amount if it is outgoing transaction for instruction
		if(TradeTxn.BUY.equals(instruction.getTradeTxn())) {
			report.addAmount(instruction.getTradeAmount());
		}
	}
}
