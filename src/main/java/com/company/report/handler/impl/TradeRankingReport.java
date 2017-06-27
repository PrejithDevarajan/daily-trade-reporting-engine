package com.company.report.handler.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.company.report.entity.TradeReport;
import com.company.report.handler.IReport;
import com.company.report.utils.CommonUtils;

/**
 * The report class which implements the logic for printing the trade ranking
 * reports. The class helps in calculating the ranking for all trade entities.
 * The class handles the incoming and outgoing ranking reports.
 *
 * The report data is held as java.util.Map which has the entity as key and the
 * trade amount for the entity as value
 *
 * It is assumed that the print logic for incoming ranking report and outgoing
 * ranking report is same.
 *
 * @author prejith.devarajan
 *
 */
public class TradeRankingReport implements IReport {
	// The map which holds the trade amount for each entity
	private Map<String, BigDecimal> entityReport = new HashMap<>();

	// The trade report enum - to identify various trade ranking reports
	private final TradeReport tradeReport;

	TradeRankingReport(TradeReport tradeReport) {
		this.tradeReport = tradeReport;
	}

	/**
	 * Constructs the text format of the report to be printed
	 *
	 * @return
	 * 			the <code>StringBuilder</code> with the text format of report
	 */
	@Override
	public StringBuilder printReport() {
		StringBuilder printBuilder = new StringBuilder();

		printBuilder.append(this.tradeReport)
					.append(":")
					.append(entityReport.size() == 0 ? "     NA" : "")
					.append("\n");

		if(entityReport.size() > 0) {
			printBuilder.append("\t\t\t    Rank  |\tEntity\n")
						.append("\t\t\t    ------|--------------------\n");
		}

		int rank = 0;
		for(Entry<String, BigDecimal> entityEntry : getRanking().entrySet()) {
			printBuilder.append("\t\t\t    ")
						.append(++rank)
						.append("     |\t")
						.append(entityEntry.getKey())
						.append(" ($")
						.append(entityEntry.getValue().setScale(3))
						.append(")\n");
		}

		return printBuilder;
	}

	/**
	 * Sums up the incoming trade amount for the specific entity. The data for
	 * each entity is stored as a java.util.Map which contains entity as the key
	 * and the trade amount for each entity as the value.
	 *
	 * @param entity
	 * 				The entity for the trade instruction
	 * @param amount
	 * 				The trade amount for the entity in the instruction
	 */
	void addEntityAmount(final String entity, final BigDecimal amount) {
		final BigDecimal entityAmount = entityReport.get(entity);

		if(entityAmount == null) {
			entityReport.put(entity, amount);
		} else {
			entityReport.put(entity, entityAmount.add(amount));
		}
	}

	/**
	 * The returned map contains the entity name as key and trade amount as
	 * value, which is sorted by the trade amount
	 *
	 * @return the ranking report
	 */
	public Map<String, BigDecimal> getRanking() {
		return CommonUtils.sortMapByValue(this.entityReport);
	}
}
