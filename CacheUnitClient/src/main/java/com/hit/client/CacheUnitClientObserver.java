package com.hit.client;

import com.hit.view.CacheUnitView;
import java.beans.PropertyChangeEvent;

/**
 * CacheUnitClientObserver class is an observer to the client's action.
 */
public class CacheUnitClientObserver extends java.lang.Object implements java.beans.PropertyChangeListener{
    private String action="";
    private CacheUnitClient cacheUnitClient;
    private CacheUnitView updateUI;
    public CacheUnitClientObserver() {
        cacheUnitClient = new CacheUnitClient();
    }

    /**
     * propertyChange function sends the new action to the cacheUnitClient's treat
     * and activates the updateUI CacheUnitView's function
     * @param evt the PropertyChangeEvent
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String res = null;
        this.action = evt.getNewValue().toString();
        res=cacheUnitClient.send(this.action);
        System.out.println(this.action);
        updateUI = (CacheUnitView) evt.getSource();
        updateUI.updateUIData(res);
    }
}
