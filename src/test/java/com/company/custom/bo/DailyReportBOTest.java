package com.company.custom.bo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.company.report.bo.DailyReportBO;
import com.company.report.entity.Instruction;
import com.company.report.entity.TradeReport;
import com.company.report.entity.TradeTxn;
import com.company.report.handler.IReport;
import com.company.report.handler.impl.TradeAmountReport;
import com.company.report.handler.impl.TradeRankingReport;


/**
 * The test class for the business object logic for generating the daily report
 * and also print the report.
 *
 * @author prejith.devarajan
 *
 */
public class DailyReportBOTest {

	@Test
	public void testGenerateDailyReport() {
		final Set<Instruction> instructions = new HashSet<>();

		// Instructions for date 12 Jun 2017 - Incoming
		instructions.add(new Instruction(
				"Entity #1",
				TradeTxn.SELL,
				BigDecimal.valueOf(0.5),
				"INR",						// currency code for rest of world region
				LocalDate.of(2017, 6, 9),
				LocalDate.of(2017, 6, 12), 	// settlement date falls on Monday
				200,
				BigDecimal.valueOf(100.25)));

		// Instructions for date 12 Jun 2017 - Incoming
		instructions.add(new Instruction(
				"Entity #2",
				TradeTxn.SELL,
				BigDecimal.valueOf(0.15),
				"AUD",						// currency code for middle east region
				LocalDate.of(2017, 6, 9),
				LocalDate.of(2017, 6, 12), 	// settlement date falls on Monday
				1000,
				BigDecimal.valueOf(95.25)));

		// Instructions for date 12 Jun 2017 - Incoming
		instructions.add(new Instruction(
				"Entity #3",
				TradeTxn.SELL,
				BigDecimal.valueOf(0.15),
				"EUR",						// currency code for rest of world region
				LocalDate.of(2017, 6, 9),
				LocalDate.of(2017, 6, 11), 	// settlement date falls on Sunday
				550,
				BigDecimal.valueOf(101.25)));

		// Instructions for date 12 Jun 2017 - Outgoing
		instructions.add(new Instruction(
				"Entity #2",
				TradeTxn.BUY,
				BigDecimal.valueOf(0.15),
				"EUR",						// currency code for rest of world region
				LocalDate.of(2017, 6, 9),
				LocalDate.of(2017, 6, 12), 	// settlement date falls on Monday
				550,
				BigDecimal.valueOf(101.25)));

		// Instructions for date 13 Jun 2017 - Outgoing
		instructions.add(new Instruction(
				"Entity #4",
				TradeTxn.BUY,
				BigDecimal.valueOf(0.15),
				"EUR",						// currency code for rest of world region
				LocalDate.of(2017, 6, 12),
				LocalDate.of(2017, 6, 13), 	// settlement date falls on Monday
				330,
				BigDecimal.valueOf(101.25)));

		// Instructions for date 13 Jun 2017 - Incoming
		instructions.add(new Instruction(
				"Entity #5",
				TradeTxn.SELL,
				BigDecimal.valueOf(0.15),
				"SGD",						// currency code for rest of world region
				LocalDate.of(2017, 6, 12),
				LocalDate.of(2017, 6, 13), 	// settlement date falls on Monday
				550,
				BigDecimal.valueOf(101.25)));

		// Instructions for date 18 Jun 2017 - Outgoing
		instructions.add(new Instruction(
				"Entity #4",
				TradeTxn.BUY,
				BigDecimal.valueOf(0.15),
				"SAR",						// currency code for middle east region
				LocalDate.of(2017, 6, 15),
				LocalDate.of(2017, 6, 16), 	// settlement date falls on Friday
				550,
				BigDecimal.valueOf(155.25)));

		// invoke business objects to generate the report
		final Map<LocalDate, Map<TradeReport, IReport>> tradeReports = new DailyReportBO().generateDailyReports(instructions);

		// check the incoming amount report - sum of all SELL instructions
		testIncomingAmountReport(tradeReports, LocalDate.of(2017, 6, 12));

		// check the outgoing amount report - sum of all BUY instructions : Date 13 Jun 2017
		testOutgoingAmountReport(tradeReports, LocalDate.of(2017, 6, 13));

		// check the incoming ranking report - ranking of entities on : Date 12 Jun 2017
		testIncomingRankingReport(tradeReports, LocalDate.of(2017, 6, 12));

		// check the outgoing ranking report - ranking of entities on : Date 13 Jun 2017
		testOutgoingRankingReport(tradeReports, LocalDate.of(2017, 6, 13));
	}

	/**
	 * Test the incoming trade amount report - sum of all SELL transactions for a specific date
	 *
	 * @param tradeReports
	 * @param date
	 */
	private void testIncomingAmountReport(final Map<LocalDate, Map<TradeReport, IReport>> tradeReports, LocalDate date) {
		final Map<TradeReport, IReport> incomingReports = tradeReports.get(date);
		final TradeAmountReport incomingReport = (TradeAmountReport) incomingReports.get(
				TradeReport.DAILY_INCOMING_AMOUNT_REPORT);
		Assert.assertEquals(BigDecimal.valueOf(32665.625), incomingReport.getAmount());
	}

	/**
	 * Test the outgoing trade amount report - sum of all SELL transactions for a specific date
	 *
	 * @param tradeReports
	 * @param date
	 */
	private void testOutgoingAmountReport(final Map<LocalDate, Map<TradeReport, IReport>> tradeReports, LocalDate date) {
		final Map<TradeReport, IReport> outgoingReports = tradeReports.get(date);
		final TradeAmountReport outgoingReport = (TradeAmountReport) outgoingReports.get(
				TradeReport.DAILY_OUTGOING_AMOUNT_REPORT);
		Assert.assertEquals(BigDecimal.valueOf(5011.875), outgoingReport.getAmount());
	}

	/**
	 * Test the incoming ranking report - ranking for entities on a specific date
	 *
	 * @param tradeReports
	 * @param date
	 */
	private void testIncomingRankingReport(final Map<LocalDate, Map<TradeReport, IReport>> tradeReports, LocalDate date) {
		final Map<TradeReport, IReport> incomingReports = tradeReports.get(date);
		final TradeRankingReport incomingRanking = (TradeRankingReport) incomingReports.get(
				TradeReport.DAILY_INCOMING_RANKING_REPORT);

		Assert.assertEquals("Entity #2", incomingRanking.getRanking().keySet().toArray()[0]);
		Assert.assertEquals("Entity #1", incomingRanking.getRanking().keySet().toArray()[1]);
		Assert.assertEquals("Entity #3", incomingRanking.getRanking().keySet().toArray()[2]);
	}

	/**
	 * Test the outgoing ranking report - ranking for entities on a specific date
	 *
	 * @param tradeReports
	 * @param date
	 */
	private void testOutgoingRankingReport(final Map<LocalDate, Map<TradeReport, IReport>> tradeReports, LocalDate date) {
		final Map<TradeReport, IReport> outgoingReports = tradeReports.get(date);
		final TradeRankingReport outgoingRanking = (TradeRankingReport) outgoingReports.get(
				TradeReport.DAILY_OUTGOING_RANKING_REPORT);

		Assert.assertEquals(1, outgoingRanking.getRanking().size());
		Assert.assertEquals("Entity #4", outgoingRanking.getRanking().keySet().toArray()[0]);
	}
}
