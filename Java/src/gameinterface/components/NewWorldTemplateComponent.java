package gameinterface.components;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import gameinterface.NewWorldTemplate;

public class NewWorldTemplateComponent extends JPanel {
	private JButton button;
	private boolean selected = false;
	private NewWorldTemplate template;
	
	public NewWorldTemplateComponent(NewWorldTemplate template) {
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

	
	public void addActionListener(ActionListener listener) {
		button.addActionListener(listener);
	}
	
	

	public void setSelected(boolean selected) {
		this.selected = selected;
		repaint();
	}


	public boolean getSelected() {
		return selected;
	}
	public NewWorldTemplate getTemplate() {
		return template;
	}
}
