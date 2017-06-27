package com.company.report.bo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.company.report.ReportFactory;
import com.company.report.entity.Instruction;
import com.company.report.entity.TradeReport;
import com.company.report.handler.IReport;
import com.company.report.handler.IReportGenerator;
import com.company.report.handler.impl.IncomingAmountReportGenerator;
import com.company.report.handler.impl.IncomingRankingReportGenerator;
import com.company.report.handler.impl.OutgoingAmountReportGenerator;
import com.company.report.handler.impl.OutgoingRankingReportGenerator;

/**
 * The Business element for the daily report feature. The class defines business
 * logic for generating the daily report and also print the report. This class
 * will be the report generation start point for any daily trade report.
 *
 *
 * @author prejith.devarajan
 *
 */
public class DailyReportBO {

	/**
	 * Generate the daily reports for the set of instructions. The method
	 * iterates through the instructions and generate the report for specific
	 * settlement date. The settlement date from the instruction will be
	 * validated for the business days and the actual settlement date is
	 * identified. The report will be generated for the identified settlement
	 * date.
	 *
	 * The method constructs a map with the settlement date as the key and the
	 * value will be the various reports for the date. The various reports' data
	 * are stored in a map - each entry in the map represents each report type.
	 * The report map will have the report identifier (enum) as key and value
	 * will be an interface which holds information based on each report type.
	 *
	 * Eg: the resulting map structure
	 * 	<map>
	 * 		<Settlement Date> - <map>
	 * 								<Report identifier (enum TradeReport)> - <interface IReport>
	 * data:
	 * <map>
	 * 		05 Jun 2017	-	<map>
	 * 							Incoming Amount Report	-	300.500
	 * 							Outgoing Amount Report	-	1209.780
	 * 							Incoming Ranking Report	-	<map>
	 * 															Entity1	 -	150.700
	 * 															Entity2  - 	52.550
	 * 														</map>
	 * 							Outgoing Ranking Report	-	<map>
	 * 															Entity2	 - 	2379.600
	 * 															Entity3	 -	500.990
	 * 														</map>
	 * 						</map>
	 * 		06 Jun 2017	-	<map>
	 * 							...
	 * 						</map>
	 * 		...
	 * 	</map>
	 *
	 * @param instructions
	 *            The set of instructions for which report has to be generated
	 * @return tradingReports The report map generated for the settlement date
	 */
	public Map<LocalDate, Map<TradeReport, IReport>> generateDailyReports(final Set<Instruction> instructions) {
		Map<LocalDate, Map<TradeReport, IReport>> tradingReports = new HashMap<>();

		instructions.forEach(instruction -> {

			// calculate the actual settlement date based on the business day
			ReportFactory.getInstance().newSettlementDateBO().calculateSettlementDate(instruction);

			final LocalDate settlementDate = instruction.getSettlementDate();

			Map<TradeReport, IReport> dailyReport = tradingReports.get(settlementDate);
			// create a new daily report if the report for the settlement date is not already available
			if( dailyReport == null) {
				dailyReport =  new HashMap<>();
				tradingReports.put(settlementDate, dailyReport);
			}

			// generate reports for all available implementations of report
			for(IReportGenerator generator : getReportGenerators() ){
				generator.generateReport(dailyReport, instruction);
			}
		});
		return tradingReports;
	}

	/**
	 * The method invokes the specific print method for each report type.
	 *
	 * @param reportData
	 * 			 the reports for the date
	 * @return the string format for the report to be printed
	 */
	public String printDailyReports(final Map<TradeReport, IReport> reportData) {
		StringBuilder printBuilder = new StringBuilder();

		// iterate the reports and invoke the specific report's print logic
		reportData.forEach((reportName, reportValue) ->
			printBuilder.append(reportValue.printReport())
		);

		return printBuilder.toString();
	}

	/**
	 * The method constructs the list of all the report generators. The report
	 * generator instances are created and added to the set which is used by
	 * invoking methods to invoke the specific report generators. For any new
	 * report implementation, a generator class needs to be added here.
	 *
	 * Note: Identifying all implementations for the IReportGenerator interface
	 * could also be done using any external library like spring framework or
	 * reflections. Inorder to minimize the use of external libraries, the
	 * reports for current requirements are hardcoded here.
	 *
	 * @return generators
	 */
	private static Set<IReportGenerator> getReportGenerators() {
		Set<IReportGenerator> generators = new HashSet<>();
		generators.add(new OutgoingRankingReportGenerator());
		generators.add(new IncomingRankingReportGenerator());
		generators.add(new OutgoingAmountReportGenerator());
		generators.add(new IncomingAmountReportGenerator());

		return generators;
	}
}
