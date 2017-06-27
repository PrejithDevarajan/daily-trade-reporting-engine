package com.company.report.bo;

import java.time.LocalDate;

import com.company.report.ReportFactory;
import com.company.report.entity.Instruction;
import com.company.report.settlement.IBusinessDays;

/**
 * The business element which defines the logic to calculate the settlement date
 * for a specified instruction.
 *
 * The BusinessDays interface is identified by the factory class based on the
 * currency. There are two implementations for BusinessDays - for the middle
 * east region and the other for the rest of world region. The BusinessDays
 * contains implementations to find the actual settlement date
 *
 * @author prejith.devarajan
 *
 */
public class SettlementDateBO {

	/**
	 * Calculates the settlement date for a given instruction. The settlement
	 * date is calculated based on the currency code for the instruction. The
	 * method identifies the implementation logic for business day based on the
	 * currency code.
	 *
	 * @param instruction
	 */
	public void calculateSettlementDate(final Instruction instruction) {

		// identify the implementation logic for settlement date based on currency
		final IBusinessDays businessDays = ReportFactory.getInstance().getBusinessDays(instruction.getCurrency());

		// get the actual settlement date if the given date falls on a weekend
		final LocalDate actualSettlementDate = businessDays.findActualSettlementDate(instruction.getSettlementDate());
		instruction.setSettlementDate(actualSettlementDate);
	}
}
