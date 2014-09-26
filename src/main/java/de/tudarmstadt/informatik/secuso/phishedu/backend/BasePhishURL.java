package de.tudarmstadt.informatik.secuso.phishedu.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * this is for internally holding the phishing urls
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public class BasePhishURL implements PhishURL, Serializable {
	private static final long serialVersionUID = 1L;
	private String[] parts = new String[0];
	private PhishSiteType siteType = PhishSiteType.AnyPhish;
	private PhishAttackType attackType = PhishAttackType.NoPhish;
	private String providerName = "";
	protected int[] correctparts = new int[0];
	/**
	 * This stores the points the user gets for his detection.
	 * 0: he got it right
	 * 1: he did not get it right
	 */
	private int[] points = {DEFAULT_CORRECT_POINTS, DEFAULT_INCORRECT_POINTS};
	
	public String[] getParts(){return parts;}
	public PhishSiteType getSiteType(){return this.siteType;}
	public PhishAttackType getAttackType(){return this.attackType;}
	public String getProviderName() {return this.providerName;}
		
	public final boolean validate(){
		return this.points[0] > 0; //if we get no points on success we can not progress.
	}
	
	public int getPoints(PhishResult result){
		boolean correct = (result == PhishResult.NoPhish_Detected || result==PhishResult.Phish_Detected);
		return points[correct ? 0 : 1];
	}
	
	public PhishResult getResult(boolean acceptance){
		PhishResult result;
		PhishAttackType attacktype = getAttackType();
		if(attacktype == PhishAttackType.NoPhish && acceptance){
			result =  PhishResult.NoPhish_Detected;
		}else if(attacktype == PhishAttackType.NoPhish && !acceptance){
			result =  PhishResult.NoPhish_NotDetected;
		}else if(attacktype != PhishAttackType.NoPhish && acceptance){
			result =  PhishResult.Phish_NotDetected;
		}else if(attacktype != PhishAttackType.NoPhish && !acceptance){
			result =  PhishResult.Phish_Detected;
		}else {
			throw new IllegalStateException("Something went horrably wrong! We should not be here.");
		}
		return result;
	}
	
	@Override
	public List<Integer> getAttackParts() {
		return new ArrayList<Integer>();
	}
	
	@Override
	public PhishURL clone(){
		BasePhishURL clone = new BasePhishURL();
		clone.attackType=this.attackType;
		clone.parts=this.parts.clone();
		clone.points=this.points.clone();
		clone.siteType=this.siteType;
		clone.correctparts=this.correctparts.clone();
		clone.validateProviderName();
		return clone;
	}
	
	@Override
	public int getDomainPart() {
		return 3;
	}
	
	public void validateProviderName() {
		if(this.providerName.equals("")){
			this.providerName = this.getParts()[3].split("\\.")[0];
			this.providerName = this.providerName.substring(0,1).toUpperCase(Locale.GERMANY) + this.providerName.substring(1);
		}
	}
	
}