package de.tudarmstadt.informatik.secuso.phishedu.backend;

import de.tu.darmstadt.R;
import de.tudarmstadt.informatik.secuso.phishedu.backend.generator.BaseGenerator;
import de.tudarmstadt.informatik.secuso.phishedu.backend.generator.KeepGenerator;

/**
 * This Class represents the information about a Level
 */
public class NoPhishLevelInfo {
	private static final double LEVEL_DISTANCE = 1.5;
	public static final int FIRST_REPEAT_LEVEL = 4;

	private static final int[] levelOutros = { 0, 0, 1337 };//TODO"R.level_02_outro"

	public float getURLTextsize() {
		float textSize = 20;

		switch (this.levelId) {
		case 0:
			// should not reach this code, as urltask is called beginning from
			// level 2
			break;
		case 1:
			// should not reach this code, as urltask is called beginning from
			// level 2
			break;
		case 2:
			textSize = 25;
			break;
		case 3:
			textSize = 25;
			break;
		case 4:
			textSize = 20;
			break;
		default:
			// this is the default for the android browser. We don't go below
			// this.
			textSize = 18;
			break;
		}

		return textSize;
	}

	private static final String[] levelTitlesIds = { R.level_title_00,
		R.level_title_01, R.level_title_02,
		R.level_title_03, R.level_title_04,
		R.level_title_05, R.level_title_06,
		R.level_title_07, R.level_title_08,
		R.level_title_09, R.level_title_10,
		R.level_title_11 };

	private static final String[] levelSubtitlesIds = {
		R.level_subtitle_00, R.level_subtitle_01,
		R.level_subtitle_02, R.level_subtitle_03,
		R.level_subtitle_04, R.level_subtitle_05,
		R.level_subtitle_06, R.level_subtitle_07,
		R.level_subtitle_08, R.level_subtitle_09,
		R.level_subtitle_10, R.level_subtitle_11 };

//	private static final int[][] levelIntroLayoutIds = {{1},{2},{3},{4},{5},{6},{7},{8}};
//
//	private static final int[][] levelFinishedLayoutIds ={{1},{2},{3},{4},{5},{6},{7},{8}};

	// For each level we can define what Attacks are applied
	// LEVEL 0-1 are empty because they don't
	public static final PhishAttackType[][] levelAttackTypes = { 
			{}, // Level 0: Awareness
			{}, // Level 1: Find URLBar in Browser
			{ PhishAttackType.Level2 }, // Level 2
			{ PhishAttackType.IP }, // Level 3
			{ PhishAttackType.TotallyUnrelated }, // Level 4
			{ PhishAttackType.Subdomain}, // Level 5
			{ PhishAttackType.HostInPath }, // Level 6
			{ PhishAttackType.Typo, PhishAttackType.Misleading }, // Level 7
			{ PhishAttackType.Homoglyphic }, // Level 8
			{ PhishAttackType.HTTP } // Level 9
	};

	public boolean hasAttack(PhishAttackType attack) {
		for (PhishAttackType attacktype : this.attackTypes) {
			if (attacktype == attack) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	private static Class[][] levelGenerators = {
		// Currently we use the same generators for all levels
		{ KeepGenerator.class }, 
	};

	public static int levelCount() {
		return levelAttackTypes.length;
	}

	public final String titleId;
	public final String subTitleId;
	public final int outroId;
//	public final int[] introLayouts;
//	public final int[] finishedLayouts;
	public final int levelId;
	public final PhishAttackType[] attackTypes;
	public final Class<? extends BaseGenerator>[] generators;
	public final String levelNumber;

	@SuppressWarnings("unchecked")
	public NoPhishLevelInfo(int levelid) {
		this.levelId = levelid;
		this.titleId = levelTitlesIds[levelid];
		this.subTitleId = levelSubtitlesIds[levelid];
//		int intro_index = Math.min(levelid, levelIntroLayoutIds.length - 1);
//		this.introLayouts = levelIntroLayoutIds[intro_index];
//		int finished_index = Math.min(levelid,
//				levelFinishedLayoutIds.length - 1);
//		this.finishedLayouts = levelFinishedLayoutIds[finished_index];
		int attacktype_index = Math.min(levelid, levelAttackTypes.length - 1);
		this.attackTypes = levelAttackTypes[attacktype_index];
		if (levelid < 2) {
			this.levelNumber = "E" + (levelid + 1);
		} else {
			this.levelNumber = Integer.toString(levelid - 1);
		}
		if (levelid < levelOutros.length) {
			this.outroId = levelOutros[levelid];
		} else {
			this.outroId = 0;
		}
		int geneator_index = Math.min(levelid, levelGenerators.length - 1);
		this.generators = levelGenerators[geneator_index];
	}

	public int getlevelPoints() {
		return BackendControllerImpl.getInstance().getLevelPoints(this.levelId);
	}

	public int weightLevelPoints(int base_points) {
		return (int) (base_points * Math.pow(LEVEL_DISTANCE, levelId));
	}

	public int levelCorrectURLs() {
		return 2;
		
//		if (levelId == 2) {
//			return 5;
//		}
//		return 6 + (2 * this.levelId);
	}

	public int levelPhishes() {
		return 2;
		
//		int base_phishes = 0;
//		if (this.levelId == 2) {
//			base_phishes = levelCorrectURLs();
//		} else {
//			base_phishes = levelCorrectURLs() / 2;
//		}
//		return base_phishes;
	}

	public int levelRepeats() {
		int result = 0;
		if (levelId >= FIRST_REPEAT_LEVEL) {
			result = (int) Math.floor(this.levelPhishes() / 2);
		}
		return result;
	}
}
