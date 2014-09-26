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
public class TotallyUnrelatedAttack extends AbstractAttack {
	int attack_url=-1;
	
	protected static final String[] PHISHER_DOMAINS = SubdomainAttack.PHISHER_DOMAINS;
	
	protected String[] getPhisherDomains(){
		return PHISHER_DOMAINS;
	}
	
	/**
	 * This constructor is required because of the implementation in {@link BackendControllerImpl#getNextUrl()}
	 * @param object This Parmeter is discarded. It is replaced by a PhishTank URL
	 */
	public TotallyUnrelatedAttack(PhishURL object) {
		super(object);
		attack_url=BackendControllerImpl.getInstance().getRandom().nextInt(getPhisherDomains().length);
	}

	@Override
	public PhishAttackType getAttackType() {
		return PhishAttackType.Subdomain;
	}
	
	@Override
	public String[] getParts() {
		String[] parts = super.getParts();
		parts[3] = getPhisherDomains()[attack_url];
		return parts;
	}
	
	@Override
	public List<Integer> getAttackParts() {
		List<Integer> result = super.getAttackParts();
		result.add(3);
		return result;
	}
}
