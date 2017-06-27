package com.company.report.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

import com.company.report.entity.Instruction;
import com.company.report.entity.TradeTxn;

/**
 * The class defines logic to read the instructions form a data feed file and
 * construct the entity <code>Instructions</code>.
 *
 * The feed file is assumed to have a predefined format.
 *
 * NOTE: It is assumed that all fields in the data feed file is mandatory and
 * validated using separate logic. The current implementation does not take care
 * of validating the fields. The class is created for the ease of testing
 * without code changes.
 *
 * @author prejith.devarajan
 *
 */
public class InstructionsCSVReader {

	/**
	 * Reads the feed data file and construct the entities for the data feed.
	 *
	 * @return instructions
	 * @throws ParseException
	 * @throws IOException
	 */
	public static final Set<Instruction> readInstructions() throws ParseException, IOException {
		// load the CSV file
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final InputStream is = classLoader.getResourceAsStream(DailyTradeReportingConstants.INSTRUCTIONS_FILE_PATH);
		final CSVReader reader = new CSVReader(new InputStreamReader(is));

		Set<Instruction> instructions = new HashSet<>();
		String[] newLine;
		// Iterate through the CSV and populate the Instructions for each line item in the feed file
		while ((newLine = reader.readNext()) != null) {
			Instruction instruction = new Instruction(
					newLine[0],
					TradeTxn.getEnum(newLine[1]),
					new BigDecimal(newLine[2]),
					newLine[3],
					LocalDate.parse(newLine[4], DateTimeFormatter.ofPattern(
							DailyTradeReportingConstants.INSTRUCTIONS_DATE_FORMAT)),
					LocalDate.parse(newLine[5], DateTimeFormatter.ofPattern(
							DailyTradeReportingConstants.INSTRUCTIONS_DATE_FORMAT)),
					Integer.parseInt(newLine[6]),
					new BigDecimal(newLine[7]));

			instructions.add(instruction);
		}
		reader.close();

		return instructions;
	}
}
