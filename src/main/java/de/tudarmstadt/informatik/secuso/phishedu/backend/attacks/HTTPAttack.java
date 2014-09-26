package de.tudarmstadt.informatik.secuso.phishedu.backend.attacks;

import java.util.List;

import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishAttackType;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

/**
 * This Attack replaces the whole URL by a replace from phishtank
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public class HTTPAttack extends AbstractAttack {
	
	/**
	 * To build an attack we need a url to decorate
	 * @param object the decorated URL
	 */
	public HTTPAttack(PhishURL object) {
		super(object);
	}

	@Override
	public PhishAttackType getAttackType() {
		return PhishAttackType.HTTP;
	}
	
	@Override
	public String[] getParts() {
		String[] parts = super.getParts();
		parts[0]="http:";
		return parts;
	}
	
	@Override
	public List<Integer> getAttackParts() {
		List<Integer> result = super.getAttackParts();
		result.add(0);
		return result;
	}
}
