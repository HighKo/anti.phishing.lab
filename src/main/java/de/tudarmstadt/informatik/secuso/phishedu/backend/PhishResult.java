package de.tudarmstadt.informatik.secuso.phishedu.backend;

/**
 * This enum shows what was the outcome of the given click of the user.
 * Based on this the Frontend can show success or fail messages.
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public enum PhishResult {
	/**
	 * The user correctly detected and identified a phish
	 */
	Phish_Detected(0),
	/**
	 * The user Detected that it was no phish
	 */
	NoPhish_Detected(1),
	/**
	 * The user could not identify the phish
	 */
	Phish_NotDetected(2),
	/**
	 * The user identified a no-phish URL as phishy
	 */
	NoPhish_NotDetected(3);
	
	private int value;

	private PhishResult(int value) {
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
	
	/**
	 * always gets the maximum of all possible values
	 * @return The maximum of all Values.
	 */
	public static int getMax(){
		int max=-1;
		for (PhishResult this_result : PhishResult.values()) {
			max=Math.max(max, this_result.getValue());
		}
		return max;
	}
}
