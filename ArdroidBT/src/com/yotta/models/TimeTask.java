package com.yotta.models;

import android.os.Handler;
import android.os.Looper;
/**
 * A class used to perform periodical updates,
 * specified inside a runnable object. An update interval
 * may be specified (otherwise, the class will perform the 
 * update every 2 seconds).
 * 
 * @author Carlos Simões
 */
public class TimeTask {
        // Create a Handler that uses the Main Looper to run in
        private Handler mHandler = new Handler(Looper.getMainLooper());

        private Runnable mStatusChecker;
        private int UPDATE_INTERVAL = 5000;

        /**
         * Creates an syncUpdater object, that can be used to
         * perform syncUpdates on a specified time interval.
         * 
         * @param syncUpdater A runnable containing the update routine.
         */
        public TimeTask(final Runnable syncUpdater) {
            mStatusChecker = new Runnable() {
                @Override
                public void run() {
                    // Run the passed runnable
                    syncUpdater.run();
                    // Re-run it after the update interval
                    mHandler.postDelayed(this, UPDATE_INTERVAL);
                }
            };
        }

        /**
         * The same as the default constructor, but specifying the
         * intended update interval.
         * 
         * @param uiUpdater A runnable containing the update routine.
         * @param interval  The interval over which the routine
         *                  should run (milliseconds).
         */
        /*public TimeTask(Runnable syncUpdater, int interval){
            UPDATE_INTERVAL = interval;
            this(syncUpdater);
        }*/

        /**
         * Starts the periodical update routine (mStatusChecker 
         * adds the callback to the handler).
         */
        public synchronized void start(){
            mStatusChecker.run();
        }

        /**
         * Stops the periodical update routine from running,
         * by removing the callback.
         */
        public synchronized void stop(){
            mHandler.removeCallbacks(mStatusChecker);
        }
}