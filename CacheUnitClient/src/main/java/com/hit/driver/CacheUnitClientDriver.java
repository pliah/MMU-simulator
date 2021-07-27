package com.hit.driver;

import com.hit.client.CacheUnitClientObserver;
import com.hit.view.CacheUnitView;

/**
 * CacheUnitClientDriver class is the builder of the program
 */
public class CacheUnitClientDriver {
    /**
     * the main function of the program
     * @param args
     */
    public static void main(String[] args) {
        CacheUnitClientObserver cacheUnitClientObserver =
                new CacheUnitClientObserver();
        CacheUnitView view = new CacheUnitView();
        view.addPropertyChangeListener(cacheUnitClientObserver);
        view.start();
    }

}
