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

public class ImageComponent extends JComponent{
	static private Color nullColor = new Color(105,98,73);
	static public Color getNullColor(){ return nullColor; }
	
	private ImageView view;
	private ImageModel model;
	private Image image;
	private JFileChooser imageChooser;
	
	public ImageComponent() {
		image = null;
		view = new ImageView();
		model = new ImageModel();
		imageChooser = new JFileChooser();
		
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.setIsPressed(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                model.fire();
                model.setIsPressed(false);
            }
        });
        model.addChangeListener(e -> repaint());
        model.addActionListener(e -> loadImageFromFile());
        
        //setPreferredSize(new Dimension(128,128));
	}

	@Override
	public void paintComponent(Graphics g) {
		view.paint((Graphics2D)g, this);
	}
	
	
	public Image getImage() {
		return image;
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

	
	/*@Override
	public void setSize(Dimension d) {
		d.width = d.height;
		super.setSize(d);
	}*/
	public void setImage(Image image) {
		this.image = image;
		model.triggerChangeListeners();
	}

    public void setIsEnabled(boolean pressed) {
    	model.setIsEnabled(pressed);
    }
	
	private void loadImageFromFile() {		
		imageChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("jpg","gif","png");
		imageChooser.addChoosableFileFilter(imageFilter);
		int result = imageChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {			
			try {
				File selectedImage = imageChooser.getSelectedFile();
				System.out.println("Image set from file, yeay !");
				setImage(ImageIO.read(selectedImage));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
}
