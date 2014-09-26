package de.tudarmstadt.informatik.secuso.phishedu.backend.attacks;

import java.util.List;

import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendControllerImpl;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishAttackType;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

/**
 * This Attack replaces the whole URL by a replace from phishtank
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public class MisleadingAttack extends AbstractAttack {
	int attackpos=0;
	int attack_domain=-1;
	private static final String[] DOMAIN_ADDITIONS={
		"login",
		"-anmelden",
		"-secure",
		"-sicher",
		"-accounts",
		"-konto",
		"-verify",
		"-verification",
		"verifizierung",
		"-signin",
		"-com",
		"-de",
		"-home",
		"update",
		"-registration",
		"registrierung",
		"-settings",
		"-einstellungen",
		"-service",
		"support",
		"-hilfe",
		"shopping",
	};
	/**
	 * This constructor is required because of the implementation in {@link BackendControllerImpl#getNextUrl()}
	 * @param object This Parmeter is discarded. It is replaced by a PhishTank URL
	 */
	public MisleadingAttack(PhishURL object) {
		super(object);
		attack_domain=BackendControllerImpl.getInstance().getRandom().nextInt(DOMAIN_ADDITIONS.length);
	}

	@Override
	public PhishAttackType getAttackType() {
		return PhishAttackType.Misleading;
	}
	
	@Override
	public String[] getParts() {
		String[] parts = super.getParts().clone();
		String domain = parts[3];
		int period = domain.lastIndexOf(".");
		String before=domain.substring(0,period);
		String after=domain.substring(period);
		domain=before+DOMAIN_ADDITIONS[attack_domain]+after;
		parts[3]=domain;
		return parts;
	}
	
	@Override
	public List<Integer> getAttackParts() {
		List<Integer> result = super.getAttackParts();
		result.add(3);
		return result;
	}
}
