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
public class SubdomainAttack extends AbstractAttack {
	int attack_url=-1;
	
	protected static final String[] PHISHER_DOMAINS ={
		"yxtar.com",//nonsense
		"badcat.de",
		"myponyfarm.com",
		"kjshkd.de",
		"ddffgh.de",
		"qieoqq.com",
		"events-ma.de",
		"hitrakstaffing.com",
		"friendsoflight.com",
		"comil11cenepa.de",
		"birdsnbeesbaby.com",
		"mabaatiroofings.com",
		"jshk.de",
		"login.com",//unrelated
		"anmeldung.de",
		"security.com",
		"security-update.com",
		"sicherheit.de",
		"accounts.com",
		"konto.de",
		"verify.com",
		"verification.com",
		"verifizierung.de",
		"signin.com",
		"home.com",
		"update.de",
		"registration.com",
		"registrierung.com",
		"settings.com",
		"einstellungen.de",
		"service.de",
		"support.com",
		"hilfe.de",
		"abo.net",
		"abonnement.biz"
	};
	
	protected String[] getPhisherDomains(){
		return PHISHER_DOMAINS;
	}
	
	/**
	 * This constructor is required because of the implementation in {@link BackendControllerImpl#getNextUrl()}
	 * @param object This Parmeter is discarded. It is replaced by a PhishTank URL
	 */
	public SubdomainAttack(PhishURL object) {
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
		ArrayList<String> adder = new ArrayList<String>(Arrays.asList(parts));
		String domain = adder.get(3);
		adder.set(3, domain+".");
		adder.add(4, getPhisherDomains()[attack_url]);
		return adder.toArray(new String[0]);
	}
	
	@Override
	public int getDomainPart() {
		return 4;
	}
	
	@Override
	public List<Integer> getAttackParts() {
		List<Integer> result = super.getAttackParts();
		result.add(3);
		return result;
	}
}
