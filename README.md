# Daily Trade Reporting Engine

A daily trade reporting engine for incoming instructions sent by various clients to the organization to execute in the international
market. 

## Business Requirements
Various clients to the organization sends in data feed for the trade transactions - to buy or sell. The major objective is to read the feed and generate reports from the trade instructions.

### Glossary
- *Entity*: A financial entity whose shares are to be bought or sold
- *Instruction Date*: Date on which the instruction was sent to the organization by various clients
- *Settlement Date*: The date on which the client wished for the instruction to be settled with respect to Instruction Date
- *Trade Transaction*: The Buy/Sell flag. B – Buy – outgoing and S – Sell – incoming
- *Agreed Fx*: The foreign exchange rate with respect to USD that was agreed
- *Units*: Number of shares to be bought or sold
- *Trade Amount* (in USD): Price per unit * Units * Agreed Fx 

### Business Days
Based on the currency for each instruction, the settlement date may change from the date specified in the data feed.
A work week starts Monday and ends Friday, unless the currency of the trade is AED or SAR, where the work week starts Sunday and ends Thursday. No other holidays to be taken into account.A trade can only be settled on a working day. If an instructed settlement date falls on a weekend, then the settlement date should be changed to the next working day.

### Objective
Create a report that shows
- Amount in USD settled incoming everyday
- Amount in USD settled outgoing everyday
- Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest amount for a buy instruction, then foo is rank 1 for outgoing

## Design

##### Entities
The instructions for each trade is represented using the ***Instruction*** entity. The entity holds the attributes Enitity name, Instruction Date , Settlement Date, BUY/SELL flag, Currency, AgreedFx, Units and Price per unit. The trade amount in USD which is agreedFx * units * price per unit, is also handled by the *Instruction* entity.

***TradeTxn***: This is an enumeration class which defines the permissible values for the trade transaction - BUY and SELL.
***TradeReport***: This is an enumeration class which represents the permissible values for the various report types. As per the current requirement the reports to be generated are:
- Incoming Amount Report
- Outgoing Amount Report
- Incoming Ranking Report
- Outgoing Ranking Report

##### Business Objects
The requirement suggests two business objects which are the implementations for handling settlement date and daily reports.
***SettlementDateBO***: is the business object which takes care of all the settlement date related business. The main business with settlement date is to identify the actual settlement date for the insturction based on the currency. It invokes the respective service class or interface to validate the settlement date and set the actual settlement date for the instruction.

***DailyReportBO***: is the business object which handles the business related to all the daily reports. The implementations handled through this business class are - generating the report and print the report. Generate report and Print report functionalities are specific to every report. 
The generate report logic iterates through the instructions and generate the report for specific for each settlement date in the instruction. The settlement date from the instruction will be validated for the business days through the *SettlementDateBO* and the actual settlement date is identified. The report will be generated for the identified settlement date by invoking the respective service classes.
The *generateDailyReports* method constructs a map with the settlement date as the key and the value will be the various reports for the date. The various reports' data are stored in a map - each entry in the map represents each report type. The report map will have the report identifier (*TradeReport*) as key and value will be an interface (*IReport*) which holds information based on each report type.
E.g.: a sample map generated from an instruction would look like this:
```
   * <map>
   *   05 Jun 2017  -  <map>
   *                     Incoming Amount Report  -  300.500
   *                     Outgoing Amount Report  -  1209.780
   *                     Incoming Ranking Report -  <map>
   *                                                  Entity1  -  150.700
   *                                                  Entity2  -  52.550
   *                                                </map>
   *                     Outgoing Ranking Report -  <map>
   *                                                  Entity2  -  2379.600
   *                                                  Entity3  -  500.990
   *                                                </map>
   *                   </map>
   *   06 Jun 2017  -  <map>
   *                     ...
   *                   </map>
   *   ...
   *  </map>
```
   
##### Handlers 
The requirement for business days are represented using the abstract class ***BusinessDays*** which implements the interface ***IBusinessDays***. The abstract class has two sub classes which populates the business days of the week based on the currency for the instruction. 
- *NormalBusinessDays* - The business days calculator for the default region of the world
- *MiddleEastWorkingDays* - The business days calculator for the middle east region, where currency is 'AED' or 'SAR'.
The *SettlementDateBO* business object invokes the *BusinessDays* interface for the validating the settlement date and finding the actual settlement date.

The reports are defined using the interface ***IReport*** which has the following implementations:
- ***TradeAmountReport*** - report for the incoming and outgoing trade amount report. The class implements the methods to calculate the total trade amount and also the logic to print the report. It is assumed that the print implementation for incoming and outgoing trade amount reports are same.
- ***TradeRankingReport*** - report for the incoming and outgoing trade ranking report. The class implements the methods to calculate the trade amount against each entity which is sorted based on the trade amount to identify the ranking. The class also handles the logic to print the report. It is assumed that the print implementation for incoming and outgoing trade ranking reports are same.

The report generation is handled through the interface ***IReportGenerator***. All implementations for the interface will be retrieved by the *DailyReportBO* business class and respective generate report method will be invoked.
Currently the following are the generate report implementations:
- IncomingAmountReportGenerator - report generator class for generating the incoming trade amount
- OutgoingAmountReportGenerator - report generator class for generating the outgoing trade amount
- IncomingRankingReportGenerator - report generator class for generating the incoming trade ranking
- OutgoingRankingReportGenerator - report generator class for generating the outgoing trade ranking

##### Common utils
The code is developed under the assumption that the data feed is received in the form of a predefined CSV file. 
***InstructionsCSVReader*** class will read the data feed file and constructs the business entities *Instruction*. The class used the external library 'opencsv' for the CSVReader utilities.

*Advantages* for the design:
- any report could be generated easily just by adding the respective implementation classes - a report generator class.
- the instructions are iterated only once to generate the reports. This helps in performance while having larger data feed.

## Running the code

The code is developed using Java 1.8 and to execute the code, build the source using Apache Maven. The following dependencies are required to build the code - JUnit (for unit tests) and opencsv (for CSV reader utilities)

The resulting jar artifact could be executed using java -jar option for the main class - ***DailyTradeReportingMain***

*DailyTradeReportingMain*: is the main class which invokes the respective classes for generatingt he daily reports from the instructions data feed.

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management


## Versioning
Current version is 1.0 and will be upgraded for any bug fixes or requirement changes

## Authors

*Prejith Devarajan* - [Email](mailto:prejith.devaraj@gmail.com)
