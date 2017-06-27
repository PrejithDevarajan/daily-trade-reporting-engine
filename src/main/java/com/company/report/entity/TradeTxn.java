package com.company.report.entity;

/**
 * Enum class defines the incoming and outgoing trade transactions.
 * 		B – Buy – outgoing
 * 		S – Sell – incoming
 *
 * @author prejith.devarajan
 *
 */
public enum TradeTxn {
	BUY("B"),
	SELL("S");

	private final String txn;

	/**
	 * @param txn
	 */
	private TradeTxn(String txn) {
		this.txn = txn;
	}

	/**
	 * Returns the respective <code>Enum</code> for the transaction text
	 *
	 * @param txn
	 * @return enum for the specified transaction text
	 */
	public static TradeTxn getEnum(final String txn) {
		if (txn != null) {
			for (TradeTxn tradeTxn : TradeTxn.values()) {
				if (txn.equalsIgnoreCase(tradeTxn.txn)) {
					return tradeTxn;
				}
			}
			throw new IllegalArgumentException("Invalid trade transaction input: " + txn);
		} else {
			throw new NullPointerException("Invalid trade transaction input: " + txn);
		}
	}
}
