package de.tudarmstadt.informatik.secuso.phishedu.backend;

import java.io.Serializable;
import java.util.Random;

import com.sun.jndi.toolkit.url.Uri;

/**
 * This is the interface that the backend presents to the frontend.
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public interface BackendController extends Serializable{
	/**
	 * This function must be called directly before the first start of the app.
	 * It will register the caller with the backend for callbacks.
	 * @param frontend This is the frontend for this backend
	 * @param doneListener This Listener is notified when init is done.
	 */
	public void init(FrontendController frontend, BackendInitListener doneListener);
	
	/**
	 * This Function allows the frontend to be notified whenever the level State changes.
	 * @param listener the frontend to notify.
	 */
	public void addOnLevelstateChangeListener(OnLevelstateChangeListener listener);
	
	/**
	 * This Function allows the frontend to unregister a listener previously registered via {@link #addOnLevelstateChangeListener(OnLevelstateChangeListener)} 
	 * @param listener the listener to unregister
	 */
	public void removeOnLevelstateChangeListener(OnLevelstateChangeListener listener);
	
	/**
	 * This Function allows the frontend to be notified whenever the level changes.
	 * @param listener the new Listener
	 */
	public void addOnLevelChangeListener(OnLevelChangeListener listener);
	
	/**
	 * This Function allows the frontend to unregister a listener previously registerd via {@link #addOnLevelChangeListener(OnLevelChangeListener)}
	 * @param listener the listener to unregister
	 */
	public void removeOnLevelChangeListener(OnLevelChangeListener listener);
		
	/**
	 * This function sends a Mail to a custom Mail Adress-
	 * @param from The Sender mail address
	 * @param to The receiver mail address
	 * @param usermessage Each send out mail contains a usermessage to maximize the awareness.
	 */
	public void sendMail(String from, String to, String usermessage);
	
	/**
	 * This starts a level and initilizes the backend state.
	 * @param level The level you want to start
	 */
	public void startLevel(int level);
	
	/**
	 * This function start the browser at the leve1 URL
	 */
	public void redirectToLevel1URL();
	
	/**
	 * Get the current url returned by the last {@link #getNextUrl()} call.
	 * @return A set of strings that (concardinated) make up the URL
	 */
	public PhishURL getUrl();
	
	/**
	 * Switch to the next URL.
	 */
	public void nextUrl();
	
	/**
	 * Get the current level of the user.
	 * This is the level the user is currently in.
	 * If we later think about allowing the user to select level we have to implement a getUnlockedLevel() Function to get the maximum available level.
	 * @return Current user level
	 */
	public int getLevel();
	
	/**
	 * Get the Level the user is able to play.
	 * @return Biggest available level
	 */
	public int getMaxUnlockedLevel();
	
	/**
	 * Get how many points the user collected.
	 * The current level Points are not added here.
	 * @return points the user has collected.
	 */
	public int getTotalPoints();
	
	/**
	 * Get how many points in the current level the user has.
	 * 
	 * It is worth to mention that these are not persistently saved.
	 * The user has to restart the given level from 0 points each time the app starts.
	 * @return points in the current level.
	 */
	public int getCurrentLevelPoints();
	
	/**
	 * Get the maximum number of points the user might get in this level.
	 * @return The maximum number of possible Points in this level.
	 */
	public int getCurrentLevelMaxPoints();
	
	/**
	 * This function is called when the user chooses weather this URL is a phish or not 
	 * @param accptance true of the user thinks this is a phish. false otherwise.
	 * @return a {@link PhishResult} enum representing the state of the phish
	 */
	public PhishResult userClicked(boolean accptance);
	
	/**
	 * This function must be called when the user selects a part of the URL as phishy (as the who-part).
	 * @param part the part that the user suspects to be an attack.
	 * @return true of the user clicked the correct part. False otherwise
	 */
	public boolean partClicked(int part);
	
	/**
	 * When the main activity receives an URI via an Indent pass it to this function so that the backend can handle it.
	 * @param data The recept URI
	 */
	public void onUrlReceive(Uri data);
	
//TODO:do we want google+
//	/**
//	 * The user clicks on the Google+ signin button.
//	 */
//	public void signIn();
//	
//	/**
//	 * The user clicks on the Google+ signout button.
//	 */
//	public void signOut();
	
	/**
	 * Delete the Data saved in the Google+ Storage
	 */
	public void deleteRemoteData();
	
	/**
	 * How many URLs must the user answer in this Level
	 * @return An Integer representing the number of URLs in this level
	 */
	public int currentLevelRequiredCorrectURLs();
	
	/**
	 * How many Phishes must the user answer in this Level
	 * @return An Integer representing the number of Phishes in this level
	 */
	public int currentLevelRequiredCorrectPhishes();
	
	/**
	 * How many URLs did the user correctly identfiy
	 * @return number of correct URLS
	 */
	public int getCorrectlyFoundURLs();
	
	/**
	 * This function restarts the current Level
	 */
	public void restartLevel();
	
	/**
	 * Return the current state of the level. This can change whenever {@link #userClicked(boolean)} or {@link #partClicked(int)} is called.
	 * @return one of the LEVEL_* constants from this interface.
	 */
	public Levelstate getLevelState();
	
	/**
	 * return the number of lifes left
	 * @return number of lifes left
	 */
	public int getLifes();
	
	/**
	 * get a phishing URL of the given type
	 * @param type The type of URL you want to get
	 * @return The (possibly decorated) Phishing url
	 */
	public PhishURL getPhishURL(PhishAttackType type);
	
	/**
	 * This function is for debugging only. It skips level0 gracefully.
	 */
	public void skipLevel0();

//	/**
//	 * Get the current game Helper. Via this Object you can access leaderboards and achievements. 
//	 * @return The current gameHelper
//	 */
//	public GameHelper getGameHelper();
	
	/**
	 * Get the number of levels the game currently supports
	 * @return number of supported levels
	 */
	public int getLevelCount();
	
	/**
	 * Get the Information about the given level
	 * @param level The number of the level you want to get
	 * @return the Level with the given number
	 */
	public NoPhishLevelInfo getLevelInfo(int level);
	
	/**
	 * Get the Info of the current Level
	 * @return The Info of the current Level
	 */
	public NoPhishLevelInfo getLevelInfo();
	
	/**
	 * Get the number of points the user got in the given level
	 * @param level The level for which you want to get the points
	 * @return the number of points for the given level
	 */
	public int getLevelPoints(int level);

	/**
	 * Test whether a given leve was completed successfully 
	 * @param level the leve to test
	 * @return true if the user completed this level previously
	 */
	public boolean getLevelCompleted(int level);
	
	/**
	 * Check if this backendcontroller is already inited.
	 * @return true if the initialization is finished. 
	 */
	public boolean isInitDone();
	
	/**
	 * this Interface is implemented by the frontend to get notified whenever the Level State changes
	 */
	public interface OnLevelstateChangeListener{
		/**
		 * This function is called when the level state changes.
		 * @param new_state the new state
		 * @param level the level that changed the state
		 */
		public void onLevelstateChange(Levelstate new_state, int level);
	}
	
	/**
	 * This Interface is implemented by the frontend to get notifed whenever the level changes
	 */
	public interface OnLevelChangeListener{
		/**
		 * This function is called when the level changes
		 * @param new_levelid the new level ID
		 */
		public void onLevelChange(int new_levelid);
	}
	
	/**
	 * This Interface is implemented by the frontend to get notified when the init is done.
	 */
	public interface BackendInitListener{
		/**
		 * This function is called while init continues.
		 * @param percent how far is completion.
		 */
		void initProgress(int percent);
		/**
		 * This function is called when the game can start.
		 */
		void onInitDone();
	}
	
	/**
	 * Represents the state of the leve
	 */
	public enum Levelstate{
		/** The Level is Running */
		running (0),
		/** The Level is finished and the user used up all lifes */
		failed (2),
		/** The Level is finished successfully */
		finished (3);
		
		private int value;
		private Levelstate(int value){
			this.value=value;
		}
		/**
		 * Get the Value for this enum
		 * @return the int value of the enum
		 */
		public int getValue(){
			return value;
		}
	}
	
	/**
	 * Get a Random object. Backendcontroller chaches this Object for all Uses in this app.
	 * @return The global random object
	 */
	public Random getRandom();
	
	/**
	 * Return whether to show the proof activity 
	 * @return true to show, false if not.
	 */
	public boolean showProof();
}

