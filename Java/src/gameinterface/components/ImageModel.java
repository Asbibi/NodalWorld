package gameinterface.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
* The model used for ImageComponent.
* 
* @see ImageComponent
*/ 
public class ImageModel {
	private boolean isEnabled;
    private ArrayList<ActionListener> actionListeners = new ArrayList<>();
    private ArrayList<ChangeListener> changeListeners = new ArrayList<>();


    /**
    * Method to call when the component is clicked
    */ 
    public void click() {
    	triggerActionListeners();
    	triggerChangeListeners();
    }
    /**
    * @return if the clickable image is enabled (= clickable)
    */ 
    public boolean getIsEnabled() { return isEnabled; }
    /**
    * @param enabled sets if the clickable image is enabled (= clickable)
    */ 
    public void setIsEnabled(boolean enabled) { isEnabled = enabled; }


    /**
    * @param actionListener the ActionListener to add
    */ 
    public void addActionListener(ActionListener listener) { actionListeners.add(listener); }
    /**
    * @param actionListener the ActionListener to remove
    */ 
    public void removeActionListener(ActionListener listener) { actionListeners.remove(listener); }
    /**
    * @param changeListener the ChangeListener to add
    */ 
    public void addChangeListener(ChangeListener listener) { changeListeners.add(listener); }
    /**
    * @param changeListener the ChangeListener to remove
    */ 
    public void removeChangeListener(ChangeListener listener) { changeListeners.remove(listener); }
    /**
    * Triggers all the ActionListeners.
    */ 
    public void triggerActionListeners() {
    	if (isEnabled) {
            for (ActionListener listener : actionListeners) {
                listener.actionPerformed(
                        new ActionEvent(this, ActionEvent.ACTION_FIRST, "triggerAL"));
            }    		
    	}
    }
    /**
    * Triggers all the ChangeListeners.
    */ 
    public void triggerChangeListeners() {
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

}
