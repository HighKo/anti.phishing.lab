package de.tudarmstadt.informatik.secuso.phishedu.backend.attacks;

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendControllerImpl;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishAttackType;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

/**
 * This Attack replaces the whole URL by a replace from phishtank
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public class PhishTankURLAttack extends AbstractAttack {
	
	/**
	 * This constructor is required because of the implementation in {@link BackendControllerImpl#getNextUrl()}
	 * @param object This Parmeter is discarded. It is replaced by a PhishTank URL
	 */
	public PhishTankURLAttack(PhishURL object) {
		super(object);
		this.object=BackendControllerImpl.getInstance().getPhishURL(PhishAttackType.AnyPhish);
	}

	@Override
	public PhishAttackType getAttackType() {
		return PhishAttackType.AnyPhish;
	}	
	
	/**
	 * As we don't know the structure of the url from phishTank. All parts might be phishy.
	 */
	@Override
	public List<Integer> getAttackParts() {
		List<Integer> result = new ArrayList<Integer>();
		for(int i=0; i<getParts().length; i++){
			result.add(i);
		}
		return result;
	}
}
