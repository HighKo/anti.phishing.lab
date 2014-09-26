package de.tudarmstadt.informatik.secuso.phishedu.backend;

import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.AbstractAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.HTTPAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.HomoglyphicAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.HostInPathAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.IPAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.KeepAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.Level2Attack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.MisleadingAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.NoPhishAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.PhishTankURLAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.SubdomainAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.TotallyUnrelatedAttack;
import de.tudarmstadt.informatik.secuso.phishedu.backend.attacks.TypoAttack;

/**
 * This enum shows what type of attack {@link BackendController#getNextUrl()} call returned.
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public enum PhishAttackType {
	/**
	 *  This Attack forces the URL to be a noPhish URL.
	 *  This is something different then the KeepAttack which just passes to the decorated object.
	 */
	NoPhish(0,NoPhishAttack.class),
	/** It is a phish but not specified */
	AnyPhish(1, PhishTankURLAttack.class),
	/** The Attack for level 2 */
	Level2(2, Level2Attack.class),
	/** Subdomains */
	Subdomain(3, SubdomainAttack.class),
	/** IP */
	IP(4, IPAttack.class),
	/** misleading */
	Misleading(7, MisleadingAttack.class),
	/** homographic */
	Homoglyphic(8, HomoglyphicAttack.class),
	/** Hostname in Path */
	HostInPath(9, HostInPathAttack.class),
	/** https -> http */
	HTTP(10, HTTPAttack.class),
	/** typo */
	Typo(11, TypoAttack.class),
	/** This Attack keeps the supplied PhishURL */
	Keep(12, KeepAttack.class),
	/** This Attack replaces the urls with something totally different */
	TotallyUnrelated(13, TotallyUnrelatedAttack.class)
	;
	
	private int value;
	private Class<? extends AbstractAttack> attack_class;

	private PhishAttackType(int value, Class<? extends AbstractAttack>  attack_class) {
		this.value = value;
		this.attack_class = attack_class;
	}
	
	/**
	 * Get the integer value of the current result.
	 * This is imported when using this enum for indexing arrays.
	 * @return the integer Value of the current result
	 */
	public int getValue() {
        return value;
    }
	
	/**
	 * Get the Attack Class for this Attack
	 * @return The Attacktype Class for this Attack
	 */
	public Class<? extends AbstractAttack> getAttackClass() {
        return attack_class;
    }
	
	/**
	 * Gets a short name for this attack.
	 * @return A Attack name String
	 */
	public String getName(){
		return super.toString()+"Attack";
	}

}
