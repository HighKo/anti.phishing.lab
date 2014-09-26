package de.tudarmstadt.informatik.secuso.phishedu.backend.attacks;


import de.tudarmstadt.informatik.secuso.phishedu.backend.AbstractUrlDecorator;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendControllerImpl;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishAttackType;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

/**
 * This Attack is a Attack that does no attack.
 * This is for simplyfing the URL generation.
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public class RandomAttack extends AbstractAttack {

	/**
	 * See {@link AbstractAttack#AbstractAttack(PhishURL)}
	 * @param object See {@link AbstractAttack#AbstractAttack(PhishURL)}
	 */
	public RandomAttack(PhishURL object) {
		//decorate the object with a random attack before calling the super constructor
		super(AbstractUrlDecorator.decorate(object, getRandomAttack()));
		
	}
	
	private static Class<? extends AbstractAttack> getRandomAttack(){
		PhishAttackType[] types = PhishAttackType.values();
		Class<? extends AbstractAttack> attack;
		do {
			attack=types[BackendControllerImpl.getInstance().getRandom().nextInt(types.length)].getAttackClass();
		} while (attack.equals(RandomAttack.class) || attack.equals(KeepAttack.class));
		return attack;
	}
	
	public PhishAttackType getAttackType() {
		return object.getAttackType();
	}
	
}
