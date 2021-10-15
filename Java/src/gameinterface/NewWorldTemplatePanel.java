package gameinterface;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
* A panel displaying a NewWorldTemplate composed on the top of the template's name, at the bottom of the 2 description lines of the template, and on the center of a button with the template's image.<br/>
* The panel can be selected or not, and its button color change depending on that value.
* 
* @see NewWorldTemplate
*/ 
public class NewWorldTemplatePanel extends JPanel {
	private JButton button;
	private boolean selected = false;
	private NewWorldTemplate template;
	
	/**
	* @param template the template represented
	*/ 
	public NewWorldTemplatePanel(NewWorldTemplate template) {
		this.template = template;
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(150,300));
		
		JLabel nameLabel = new JLabel(template.getName(), JLabel.CENTER);
		add(nameLabel, BorderLayout.NORTH);
		
		button = new JButton() {
			@Override public void paintComponent(Graphics g) {
				g.setColor(selected ? Color.lightGray : Color.darkGray);
				g.fillRect(0, 0, getWidth(), getHeight());
				int size = getSize().width;
				g.drawImage(template.getImage(), (getWidth() - size)/2, (getHeight() - size)/2, size, size, null);
			}
			@Override public Dimension getSize() {
				Dimension d = super.getSize();
				int size = Math.min(d.width, d.height);
				return new Dimension(size,size);
			}
		};
		add(button, BorderLayout.CENTER);
		

		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new GridLayout(2,0));
		descriptionPanel.add(new JLabel(template.getDescriptionL1(), JLabel.CENTER));
		descriptionPanel.add(new JLabel(template.getDescriptionL2(), JLabel.CENTER));
		add(descriptionPanel, BorderLayout.SOUTH);
	}

	/**
	* @param actionListener the listener to add to the button's ActionListener's list
	*/ 
	public void addActionListener(ActionListener listener) {
		button.addActionListener(listener);
	}
	
	
	/**
	* @param selected sets if the template is selected or not
	*/ 
	public void setSelected(boolean selected) {
		this.selected = selected;
		repaint();
	}

	/**
	* @return if the panel is selected
	*/ 
	public boolean getSelected() {
		return selected;
	}
	/**
	* @return the template associated to the panel
	*/ 
	public NewWorldTemplate getTemplate() {
		return template;
	}
}
