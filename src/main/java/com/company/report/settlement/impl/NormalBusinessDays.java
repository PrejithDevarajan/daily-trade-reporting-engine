package com.company.report.settlement.impl;

import java.time.DayOfWeek;

/**
 * The class implements the logic to identify the business days for the rest of world region.
 *
 * @author prejith.devarajan
 *
 */
public class NormalBusinessDays extends BusinessDays {

	private static NormalBusinessDays instance = null;

	NormalBusinessDays() {
		super();
	}

	/**
	 * A work week starts Monday and ends Friday for rest of world region
	 */
	@Override
	protected void identifyBusinessDays() {
		this.businessDays.add(DayOfWeek.MONDAY);
		this.businessDays.add(DayOfWeek.TUESDAY);
		this.businessDays.add(DayOfWeek.WEDNESDAY);
		this.businessDays.add(DayOfWeek.THURSDAY);
		this.businessDays.add(DayOfWeek.FRIDAY);
	}

    public static NormalBusinessDays getInstance() {
        if (instance == null) {
            instance = new NormalBusinessDays();
        }
        return instance;
    }
}
