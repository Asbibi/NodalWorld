package gameinterface.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import gamelogic.ImageFile;

/**
* A clickable display of an Image object.
* When clicked, it opens a JFileChooser to replace the image.
* 
* @see ImageModel
* @see ImageView
*/ 
public class ImageComponent extends JComponent{
	static private Color nullColor = new Color(105,98,73);
	/**
	* @return the color to display as a rectangle when the image is null or empty
	*/ 
	static public Color getNullColor(){ return nullColor; }
	
	private ImageView view;
	private ImageModel model;
	private ImageFile image;
	private JFileChooser imageChooser;
	
	public ImageComponent() {
		image = null;
		view = new ImageView();
		model = new ImageModel();
		imageChooser = new JFileChooser();
		
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.click();
            }
        });
        model.addChangeListener(e -> repaint());
        model.addActionListener(e -> setImageFromFile());
	}

	
	@Override
	public void paintComponent(Graphics g) {
		view.paint((Graphics2D)g, this);
	}
	
	

	@Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(128, 128);
    }
	/**
	* @return the image displayed
	*/ 
	public Image getImage() {
		if (image == null)
			return null;
		return image.getImage();
	}
	/**
	* @return the image displayed
	*/ 
	public ImageFile getImageFile() {
		return image;
	}

	
	
	
	/**
	* @param if the image component should be enabled or not
	*/ 
    public void setIsEnabled(boolean enabled) {
    	model.setIsEnabled(enabled);
    }
	/**
	* @param the image displayed
	*/ 
	public void setImage(ImageFile image) {
		this.image = image;
		model.triggerChangeListeners();
	}
	/**
	* Sets the image displayed using a JFileChooser.
	*/ 
	private void setImageFromFile() {		
		imageChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		imageChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Images", "jpg","gif","png");
		imageChooser.addChoosableFileFilter(imageFilter);
		int result = imageChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedImage = imageChooser.getSelectedFile();
			image = new ImageFile(selectedImage.getPath());
		}		
	}
}
