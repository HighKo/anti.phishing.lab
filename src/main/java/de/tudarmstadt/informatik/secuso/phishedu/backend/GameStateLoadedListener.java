package de.tudarmstadt.informatik.secuso.phishedu.backend;

/**
 * This Interface is used internally to notify the Backend about the finished load of the GameState
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public interface GameStateLoadedListener {
	/**
	 * Notify the backend about the finished gamestate load.
	 */
	public void onGameStateLoaded();
}
