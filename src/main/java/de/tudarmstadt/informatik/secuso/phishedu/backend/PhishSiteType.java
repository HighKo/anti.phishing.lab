package de.tudarmstadt.informatik.secuso.phishedu.backend;

/**
 * This enum shows what type of phish the last {@link BackendController#getNextUrl()} call returned.
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public enum PhishSiteType {
	/** It is a phish but not specified */
	AnyPhish(1),
	/** Something completly different */
	Generic(2),
	
	/** e.g. Facebook, G+ */
	Social(3),
	/** e.g. Banking, Paypal */
	Financial(4), //including PaymentServices
	/** e.g. ebay, amazon */
	Shopping(5), //including Auction
	/** e.g. Telekom, Unitymedia */
	ISP(6),
	/** e.g. WOW */
	Gaming(7),
	/** e.g. Finanzamt, Ausweisapp */
	Government(8),
	/** e.g. News */
	Information(9),
	/** e.g. Messenger, ICQ*/
	Communication(10),
	/** e.g. Xing */
	JobMarket(11),
	/** e.g. Dropbox, G-Drive */
	DataExchange(12);
	
	private int value;

	private PhishSiteType(int value) {
		this.value = value;
	}
	
	/**
	 * Get the integer value of the current result.
	 * This is imported when using this enum for indexing arrays.
	 * @return the integer Value of the current result
	 */
	public int getValue() {
        return value;
    }
}
