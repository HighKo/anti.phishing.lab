package de.tudarmstadt.informatik.secuso.phishedu.backend.attacks;

import java.io.Serializable;

import de.tudarmstadt.informatik.secuso.phishedu.backend.AbstractUrlDecorator;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishAttackType;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishResult;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

/**
 * This is the abstract implementation for following Attacks.
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public abstract class AbstractAttack extends AbstractUrlDecorator implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * To build an attack we need a url to decorate
	 * @param object the decorated URL
	 */
	public AbstractAttack(PhishURL object){
		super(object);
		this.object=object;
	}
	
	@Override
	public abstract PhishAttackType getAttackType();
	
	@Override
	public PhishResult getResult(boolean acceptance) {
		if(acceptance){
			return PhishResult.Phish_NotDetected;
		}else{
			return PhishResult.Phish_Detected;
		}
	}
	
}
