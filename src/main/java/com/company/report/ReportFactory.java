package com.company.report;

import com.company.report.bo.DailyReportBO;
import com.company.report.bo.SettlementDateBO;
import com.company.report.settlement.IBusinessDays;
import com.company.report.settlement.impl.MiddleEastBusinessDays;
import com.company.report.settlement.impl.NormalBusinessDays;

/**
 * The class uses factory methods to deal with the problem of creating objects
 * without having to specify the exact class of the object that will be created.
 * This is done by creating objects by calling a factory method—either specified
 * in an interface and implemented by child classes, or implemented in a base
 * class and optionally overridden by derived classes.
 *
 * @author prejith.devarajan
 *
 */
public class ReportFactory {

	private static ReportFactory instance = null;

	/**
	 * The method returns the singleton instance for the ReportFactory class
	 *
	 * @return the instance of ReportFactory
	 */
	public static ReportFactory getInstance() {
		if (instance == null) {
			instance = new ReportFactory();
		}
		return instance;
	}

	/**
	 * The method constructs the instance of Daily report business object. The
	 * method will not expose the exact class of the object that will be
	 * created.
	 *
	 * @return the instance of DailyReportBO
	 */
	public DailyReportBO newDailyReportBO() {
		return new DailyReportBO();
	}

	/**
	 * The method constructs the instance of Settlement date business object.
	 * The method will not expose the exact class of the object that will be
	 * created.
	 *
	 * @return the instance of SettlementDateBO
	 */
	public SettlementDateBO newSettlementDateBO() {
		return new SettlementDateBO();
	}

	/**
	 * Identifies the implementation class for identifying the business days
	 * based on currency. Depending on the currency of each instruction the
	 * settlement date may change.
	 *
	 * Middle East region will have different set of business days and we
	 * identify if the currency is AED or SAR
	 *
	 * For all other currencies the business days identification logic resides
	 * in the NormalBusinessDays
	 *
	 * @param currency
	 * @return the implementation class for the business days
	 */
	public IBusinessDays getBusinessDays(final String currency) {
		if ("AED".equalsIgnoreCase(currency) || "SAR".equalsIgnoreCase(currency)) {
			// check if currency is AED or SAR for Middle East region
			return MiddleEastBusinessDays.getInstance();
		} else {
			// for all other currencies
			return NormalBusinessDays.getInstance();
		}
	}
}
