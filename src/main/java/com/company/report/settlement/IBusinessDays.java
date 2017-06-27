package com.company.report.settlement;

import java.time.LocalDate;

/**
 * The class defines the business days and calculate the settlement date
 * based on the business days for a region.
 *
 * @author prejith.devarajan
 *
 */
@FunctionalInterface
public interface IBusinessDays {

	/**
	 * Find the actual settlement date based on the business days.
	 * If an instructed settlement date falls on a weekend, then the
	 * settlement date should be changed to the next working day.
	 *
	 * @param date
	 * @return actual settlement date based on business days
	 */
	LocalDate findActualSettlementDate(final LocalDate date);
}
