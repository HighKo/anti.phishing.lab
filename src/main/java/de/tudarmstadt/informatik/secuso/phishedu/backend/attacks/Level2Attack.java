package de.tudarmstadt.informatik.secuso.phishedu.backend.attacks;


import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishAttackType;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

/**
 * This Attack is special for Level2
 * It will accept the click on the domain name only.
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public class Level2Attack extends AbstractAttack {

	/**
	 * See {@link AbstractAttack#AbstractAttack(PhishURL)}
	 * @param object See {@link AbstractAttack#AbstractAttack(PhishURL)}
	 */
	public Level2Attack(PhishURL object) {super(object);}
	
	public PhishAttackType getAttackType() {
		return PhishAttackType.Level2;
	}
	
}
