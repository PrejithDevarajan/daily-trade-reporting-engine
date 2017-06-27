package com.company.custom.bo;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.company.report.ReportFactory;
import com.company.report.entity.Instruction;
import com.company.report.entity.TradeTxn;

/**
 * The test class for the logic to calculate the settlement date for a specified instruction.
 *
 * @author prejith.devarajan
 *
 */
public class SettlementDateBOTest {

	/**
	 * Test the settlement date calculation logic.
	 * 		Scenario:	Currency - AED
	 * 					Trade - SELL
	 * 					Settlement Day - Friday
	 * @throws Exception
	 */
	@Test
	public void testCalculateSettlementDateAEDFriday() throws Exception {
		final Instruction instruction = new Instruction(
				"Smart Trade",
				TradeTxn.SELL,
				BigDecimal.valueOf(0.5),
				"AED",						// currency code for middle east region
				LocalDate.of(2017, 1, 5),
				LocalDate.of(2017, 1, 6), 	// settlement date falls on Friday
				200,
				BigDecimal.valueOf(100.25));

		// invoke business object to calculate settlement date
		ReportFactory.getInstance().newSettlementDateBO().calculateSettlementDate(instruction);

		// check against the expected value - the next business day for the current settlement date
		Assert.assertEquals(LocalDate.of(2017, 1, 8), instruction.getSettlementDate());
	}

	/**
	 * Test the settlement date calculation logic.
	 * 		Scenario:	Currency - SAR
	 * 					Trade - SELL
	 * 					Settlement Day - Monday
	 * @throws Exception
	 */
	@Test
	public void testCalculateSettlementDateSARMonday() throws Exception {
		final Instruction instruction = new Instruction(
				"Smart Trade",
				TradeTxn.SELL,
				BigDecimal.valueOf(0.5),
				"SAR",						// currency code for middle east region
				LocalDate.of(2017, 6, 16),
				LocalDate.of(2017, 6, 19), 	// settlement date falls on Monday
				200,
				BigDecimal.valueOf(100.25));

		// invoke business object to calculate settlement date
		ReportFactory.getInstance().newSettlementDateBO().calculateSettlementDate(instruction);

		// check against the expected value - same settlement date as in instruction
		Assert.assertEquals(LocalDate.of(2017, 6, 19), instruction.getSettlementDate());
	}

	/**
	 * Test the settlement date calculation logic.
	 * 		Scenario:	Currency - EUR
	 * 					Trade - SELL
	 * 					Settlement Day - Saturday
	 * @throws Exception
	 */
	@Test
	public void testCalculateSettlementDateEURSaturday() throws Exception {
		final Instruction instruction = new Instruction(
				"Smart Trade",
				TradeTxn.SELL,
				BigDecimal.valueOf(0.5),
				"EUR",						// currency code for rest of world region
				LocalDate.of(2017, 1, 6),
				LocalDate.of(2017, 1, 7), 	// settlement date falls on Saturday
				200,
				BigDecimal.valueOf(100.25));

		// invoke business object to calculate settlement date
		ReportFactory.getInstance().newSettlementDateBO().calculateSettlementDate(instruction);

		// check against the expected value - the next business day for the current settlement date
		Assert.assertEquals(LocalDate.of(2017, 1, 9), instruction.getSettlementDate());
	}

	/**
	 * Test the settlement date calculation logic.
	 * 		Scenario:	Currency - EUR
	 * 					Trade - SELL
	 * 					Settlement Day - Monday
	 * @throws Exception
	 */
	@Test
	public void testCalculateSettlementDateEURMonday() throws Exception {
		final Instruction instruction = new Instruction(
				"Smart Trade",
				TradeTxn.SELL,
				BigDecimal.valueOf(0.5),
				"EUR",						// currency code for rest of world region
				LocalDate.of(2017, 6, 16),
				LocalDate.of(2017, 6, 19), 	// settlement date falls on Monday
				200,
				BigDecimal.valueOf(100.25));

		// invoke business object to calculate settlement date
		ReportFactory.getInstance().newSettlementDateBO().calculateSettlementDate(instruction);

		// check against the expected value - same settlement date as in instruction
		Assert.assertEquals(LocalDate.of(2017, 6, 19), instruction.getSettlementDate());
	}
}
