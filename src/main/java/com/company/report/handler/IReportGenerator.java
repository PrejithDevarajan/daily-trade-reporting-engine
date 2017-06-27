package com.company.report.handler;

import java.util.Map;

import com.company.report.entity.Instruction;
import com.company.report.entity.TradeReport;

/**
 * The interface class which defines the daily report generation. The class
 * defines methods specific to generate the respective report.
 *
 * Any specific report needs to implement this class and override the generate
 * report for specific report generation logic.
 *
 * @author prejith.devarajan
 *
 */
@FunctionalInterface
public interface IReportGenerator {

	/**
	 * Defines the report generation logic
	 *
	 * @param tradeReport
	 * 					the report data for the specific report type
	 * @param instruction
	 * 					the instructions entity to be considered for generating report
	 */
	void generateReport(Map<TradeReport, IReport> tradeReport, Instruction instruction);
}
