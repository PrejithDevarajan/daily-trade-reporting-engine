package com.company.report.utils;

/**
 * The class defines all constants which are used in the code for daily trade
 * reporting features.
 *
 * @author prejith.devarajan
 *
 */
public class DailyTradeReportingConstants {

	// The name of the settlement instruction CSV file.
	public static final String INSTRUCTIONS_FILE_PATH = "trade-intructions.csv";

	// The delimeter which separates the date fields in the instruction CSV file
	public static final String INSTRUCTIONS_CSV_DELIMETER = ",";

	// The common date format for the instruction date (actual and settlement)
	public static final String INSTRUCTIONS_DATE_FORMAT = "dd MMM yyyy";

	// The currency scale value in USD for the trade amount
	public static final int CURRENCY_DECIMAL_SCALE_VALUE = 3;
}
