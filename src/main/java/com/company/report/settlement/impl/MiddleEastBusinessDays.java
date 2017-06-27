package com.company.report.settlement.impl;

import java.time.DayOfWeek;

/**
 * The class implements the logic to identify the business days for the middle east region.
 *
 * @author prejith.devarajan
 *
 */
public class MiddleEastBusinessDays extends BusinessDays {

	private static MiddleEastBusinessDays instance = null;

	MiddleEastBusinessDays() {
		super();
	}

	/**
	 * The work week starts Sunday and ends Thursday
	 */
	@Override
	protected void identifyBusinessDays() {
		this.businessDays.add(DayOfWeek.SUNDAY);
		this.businessDays.add(DayOfWeek.MONDAY);
		this.businessDays.add(DayOfWeek.TUESDAY);
		this.businessDays.add(DayOfWeek.WEDNESDAY);
		this.businessDays.add(DayOfWeek.THURSDAY);
	}

    public static MiddleEastBusinessDays getInstance() {
        if (instance == null) {
            instance = new MiddleEastBusinessDays();
        }
        return instance;
    }
}
