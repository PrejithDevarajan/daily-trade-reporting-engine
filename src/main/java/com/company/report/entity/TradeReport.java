package com.company.report.entity;

/**
 * Enum class defines all the trade reports. This enum holds identifiers for
 * each report types.
 *
 * @author prejith.devarajan
 *
 */
public enum TradeReport {
	DAILY_OUTGOING_AMOUNT_REPORT("DAILY OUTGOING AMOUNT"),
	DAILY_INCOMING_AMOUNT_REPORT("DAILY INCOMING AMOUNT"),
	DAILY_OUTGOING_RANKING_REPORT("DAILY OUTGOING RANKING"),
	DAILY_INCOMING_RANKING_REPORT("DAILY INCOMING RANKING");

	private final String report;

	/**
	 * @param report
	 */
	private TradeReport(String report) {
		this.report = report;
	}

	/**
	 * Returns the respective <code>Enum</code> for the report text
	 *
	 * @param report
	 * @return enum for the report value
	 */
	public static TradeReport getEnum(final String report) {
		for (TradeReport tradeReport : TradeReport.values()) {
			if (report.equalsIgnoreCase(tradeReport.report)) {
				return tradeReport;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return report;
	}
}
