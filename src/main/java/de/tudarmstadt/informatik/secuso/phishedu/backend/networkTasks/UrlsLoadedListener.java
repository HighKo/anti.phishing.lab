package de.tudarmstadt.informatik.secuso.phishedu.backend.networkTasks;

import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishAttackType;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

/**
 * This interface is for the BackendController to get notified when the {@link GetUrlsTask} is finished
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public interface UrlsLoadedListener {
	/**
	 * Returns the downloaded URLs 
	 * @param urls The downloaded urls
	 * @param type The type of urls
	 */
	public void urlsReturned(PhishURL[] urls, PhishAttackType type);
	/**
	 * Notifies the Listener for progress. This allows us to display a progressbar.
	 * @param percent What is the current progress.
	 */
	public void urlDownloadProgress(int percent);
}
