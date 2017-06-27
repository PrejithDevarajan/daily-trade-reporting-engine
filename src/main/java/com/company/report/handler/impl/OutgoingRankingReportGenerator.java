package com.company.report.handler.impl;

import java.util.Map;

import com.company.report.entity.Instruction;
import com.company.report.entity.TradeReport;
import com.company.report.entity.TradeTxn;
import com.company.report.handler.IReport;
import com.company.report.handler.IReportGenerator;

/**
 * The implementation class for the outgoing ranking report. The class defines the
 * logic to generate the outgoing ranking report.
 *
 * @author prejith.devarajan
 *
 */
public class OutgoingRankingReportGenerator implements IReportGenerator {

	/**
	 * Defines the report generation logic. For the
	 * <code>TradeRankingReport</code> sum up the incoming trade ranking for
	 * each instruction.
	 *
	 * The method puts a new report type to the map if a report for the specific
	 * type is not already available. It is also considered that a report for
	 * all report types will be created even if data for that specific report
	 * type is available in the instructions. Means, if there is no outgoing
	 * trade available in the instructions, the outgoing ranking report will
	 * still display an entry with NO REPORT
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
		TradeRankingReport report = (TradeRankingReport) tradeReport.get(TradeReport.DAILY_OUTGOING_RANKING_REPORT);
		/*
		 * Create new report instance if no report for the settlement date is
		 * already defined. This creates an entry even if the whole instructions
		 * does not contain an incoming trade instruction.
		 */
		if(report == null){
			report = new TradeRankingReport(TradeReport.DAILY_OUTGOING_RANKING_REPORT);
			tradeReport.put(TradeReport.DAILY_OUTGOING_RANKING_REPORT, report);
		}

		/*
		 * Sum the trade amount if it is outgoing transaction for the entity of
		 * instruction. The ranking reports hold a map with entity as the key
		 * and the trade amount for entity as value.
		 */
		if(TradeTxn.BUY.equals(instruction.getTradeTxn())) {
			report.addEntityAmount(instruction.getEntity(), instruction.getTradeAmount());
		}
	}
}
