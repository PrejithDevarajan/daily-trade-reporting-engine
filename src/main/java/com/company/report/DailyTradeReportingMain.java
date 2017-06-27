package com.company.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.company.report.bo.DailyReportBO;
import com.company.report.entity.TradeReport;
import com.company.report.handler.IReport;
import com.company.report.utils.DailyTradeReportingConstants;
import com.company.report.utils.InstructionsCSVReader;

/**
 * The main class for the Daily Trade Reporting Engine. The class will invoke
 * the business objects which in-turn invokes the report generator classes.
 *
 * The main objective of the code is to construct a java.util.Map instance which
 * holds reports for specific settlement dates. It has 'Settlement Date' as the
 * key and a 'Map of reports' for the settlement date as the value. The 'Map of
 * reports' defines the various report data - which has the report name (enum)
 * as key and the interface IReport which holds the report data, as the value.
 *
 * The report is generated using the the instruction data which is assumed to be
 * a data feed in a pre-defined CSV format. There is a separate CSV reader to
 * read the data feed file and populate the Instructions entity
 *
 * @author prejith.devarajan
 *
 */
public class DailyTradeReportingMain {

	private DailyTradeReportingMain() {
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		/*
		 * The business classes are invoked to retrieve the report map. The
		 * report map holds the report for specific settlement dates.
		 */

		// invoke the factory to get instance of DailyReportBO
		DailyReportBO reportBO = ReportFactory.getInstance().newDailyReportBO();
		/*
		 * Read the instructions using the CSV reader utility and invoke the
		 * business class which contains the implementation logic to generate
		 * the daily reports.
		 */
		final Map<LocalDate, Map<TradeReport, IReport>> tradingReports = reportBO
				.generateDailyReports(InstructionsCSVReader.readInstructions());

		/*
		 * Display the reports in the console
		 */
		StringBuilder reportBuilder = new StringBuilder();
		reportBuilder.append("                DAILY TRADE REPORTING ENGINE              \n")
					 .append("---------------------------------------------------------\n");

		// Iterate through the report date and print report for specific date
		tradingReports.forEach((key, value) -> {
			reportBuilder.append("\nDATE: ")
						 .append(key.format(DateTimeFormatter.ofPattern(
								 DailyTradeReportingConstants.INSTRUCTIONS_DATE_FORMAT)))
						 .append("\n------------------\n")
						 // print various reports for the settlement date
						 .append(reportBO.printDailyReports(value));
		});
		reportBuilder.append("---------------------------------------------------------");

		System.out.println(reportBuilder.toString());
	}
}
