package gameinterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageModel {
	private boolean isPressed; // is being pushed
    private ArrayList<ActionListener> actionListeners = new ArrayList<>(); // triggering/fire button
    private ArrayList<ChangeListener> changeListeners = new ArrayList<>();// change listening


    public boolean isPressed() {
        return this.isPressed;
    }
    public void setPressed(boolean pressed) {
        if (pressed != isPressed) {
            this.isPressed = pressed;
            //triggerChangeListeners();
        }
    }


    public void addActionListener(ActionListener listener) { actionListeners.add(listener); }
    public void removeActionListener(ActionListener listener) { actionListeners.remove(listener); }
    public void addChangeListener(ChangeListener listener) { changeListeners.add(listener); }
    public void removeChangeListener(ChangeListener listener) { changeListeners.remove(listener); }

    
    
    public void fire() {
    	fireActionListeners();        
    }
    private void fireActionListeners() {
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(
                    new ActionEvent(this, ActionEvent.ACTION_FIRST, "fire"));
        }
    }

    public void triggerChangeListeners() {
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

}
