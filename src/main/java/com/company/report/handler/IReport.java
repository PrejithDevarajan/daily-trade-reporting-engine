package com.company.report.handler;

/**
 * The interface class which defines the daily report. The class defines methods
 * specific to displaying the report.
 *
 * Any specific report type needs to implement this class and override the print
 * method for specific display logic.
 *
 * @author prejith.devarajan
 *
 */
@FunctionalInterface
public interface IReport {

	/**
	 * Constructs the text format of the report to be printed
	 *
	 * @return display text for the report
	 */
	StringBuilder printReport();
}
