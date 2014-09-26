package de.tudarmstadt.informatik.secuso.phishedu.backend.attacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendControllerImpl;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishAttackType;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

/**
 * This Attack replaces the whole URL by a replace from phishtank
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public class HostInPathAttack extends AbstractAttack {
	
	protected static final String[] PHISHER_DOMAINS = SubdomainAttack.PHISHER_DOMAINS;
	
	/**
	 * To build an attack we need a url to decorate
	 * @param object the decorated URL
	 */
	public HostInPathAttack(PhishURL object) {
		super(object);
		phish_domain=BackendControllerImpl.getInstance().getRandom().nextInt(PHISHER_DOMAINS.length);
	}

	@Override
	public PhishAttackType getAttackType() {
		return PhishAttackType.HostInPath;
	}
	private int phish_domain=-1;
	
	@Override
	public String[] getParts() {
		String[] parts = super.getParts().clone();
		ArrayList<String> adder = new ArrayList<String>(Arrays.asList(parts));
		String[] address = new String[]{parts[0],parts[1],parts[2],parts[3]};
		adder.set(2, "");
		adder.set(3, PHISHER_DOMAINS[phish_domain]);
		adder.addAll(5, Arrays.asList(address));
		return adder.toArray(new String[0]);
	}
	
	@Override
	public List<Integer> getAttackParts() {
		List<Integer> result = super.getAttackParts();
		result.add(5);
		return result;
	}
}
