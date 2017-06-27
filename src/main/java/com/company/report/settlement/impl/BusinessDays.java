package com.company.report.settlement.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.company.report.settlement.IBusinessDays;

/**
 * The class implements the logic to identify the actual settlement date. If an instructed
 * settlement date falls on a weekend, then the settlement date should be changed to the
 * next working day.
 *
 * @author prejith.devarajan
 *
 */
public abstract class BusinessDays implements IBusinessDays {

	public BusinessDays() {
		identifyBusinessDays();
	}

	protected Set<DayOfWeek> businessDays = new HashSet<>();

	protected abstract void identifyBusinessDays();

	/**
	 * The method checks for a date if it comes under a business day. If not recursively
	 * invoke the same until a date on a business day is identified.
	 *
	 * @param date - the date to check if it falls on a business day
	 * @return LocalDate
	 * 				the date which falls on a business day
	 */
	@Override
	public LocalDate findActualSettlementDate(final LocalDate date) {
		final DayOfWeek dayOfSettlementDate = date.getDayOfWeek();

		if(businessDays.contains(dayOfSettlementDate)) {
			// return the date if it is a business day
			return date;
		} else {
			return findActualSettlementDate(date.plusDays(1));
		}
	}
}
