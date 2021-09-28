package gameinterface;

import java.awt.*;
import javax.swing.JComponent;

public class ImageComponent extends JComponent{
	static private Color nullColor = new Color(105,98,73);
	static public Color getNullColor(){ return nullColor; }
	
	private ImageView view;
	private ImageModel model;
	private Image image;
	
	public ImageComponent() {
		image = null;
		view = new ImageView();
		model = new ImageModel();
        model.addChangeListener(e -> repaint());
        model.addActionListener(e -> loadImageFromFile());
        setPreferredSize(new Dimension(128,128));
	}

	@Override
	public void paintComponent(Graphics g) {
		view.paint((Graphics2D)g, this);
	}
	
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
		model.triggerChangeListeners();
	}
	
	public void loadImageFromFile() {
		System.out.println("Image set from file, yeay !");
	}
}
