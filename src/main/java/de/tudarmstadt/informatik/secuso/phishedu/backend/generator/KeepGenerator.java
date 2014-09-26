package de.tudarmstadt.informatik.secuso.phishedu.backend.generator;

import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

/**
 * This Generator passes the decorated URL without modification. 
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public class KeepGenerator extends BaseGenerator {

	/**
	 * See {@link BaseGenerator#BaseGenerator(PhishURL)}
	 * @param object See {@link BaseGenerator#BaseGenerator(PhishURL)}
	 */
	public KeepGenerator(PhishURL object) {super(object);}
}
