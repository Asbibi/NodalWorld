package gameinterface;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* This class holds all the information needed by the GameManagerBuilder class to create the wanted GameManager.
* 
* @see NewWorldDialogBox
* @see GameManagerBuilder
*/
public class NewWorldTemplate {
	public static NewWorldTemplate empty 					= new NewWorldTemplate("Empty", 			"A completely empty world.", "",								"res/_System_Template_Empty.png",			false, true);
	public static NewWorldTemplate basic 					= new NewWorldTemplate("Basic", 			"An empty world but with", "already existing assets.", 			"res/_System_Template_Basic.png",			false, true);
	public static NewWorldTemplate island 					= new NewWorldTemplate("Island", 			"A simple world with an", "island shaped terrain.",				"res/_System_Template_Island.png", 			false, true);
	public static NewWorldTemplate completeDemo				= new NewWorldTemplate("Demo", 				"A complete demo world to", "use as example.",					"res/_System_Template_Demo.png", 			false, false);
	
	public static NewWorldTemplate loadElements 			= new NewWorldTemplate("Import Data", 		"Load surfaces & species", "from a save file.", 				"res/_System_Template_LoadElements.png", 	true, true);
	public static NewWorldTemplate loadElementsTerrain 		= new NewWorldTemplate("Import Terrain",	"Load surfaces, species &", "terrain from a save file.",		"res/_System_Template_LoadEleTerrain.png", 	true, false);
	public static NewWorldTemplate loadElementsAllNodes 	= new NewWorldTemplate("Import Graphs", 	"Load surfaces, species &", "all graphs from a save file.", 	"res/_System_Template_LoadEleTerGraphs.png",true, false);
	public static NewWorldTemplate loadFullSave 			= new NewWorldTemplate("Complete Load", 	"Resume from a save file.", "", 								"res/_System_Template_LoadFull.png", 		true, false);
	
	// ====================================================
	
	
	private static int idCounter = 0;
	
	private int id;
	private String name;
	private String descriptionL1;
	private String descriptionL2;
	private Image image;
	private boolean askDimension;
	private boolean askLoading;
	
	/**
	* @param name the template name (display)
	* @param descriptionL1 the first line of the template description (display)
	* @param descriptionL1 the second line of the template description (display)
	* @param name the path to the template image file (display)
	* @param askLoading indicates if the template requires to load a savefile
	* @param askDimension indicates if the template requires to input terrain dimensions
	*/
	public NewWorldTemplate(String name, String descriptionL1, String descriptionL2, String imagePath, boolean askLoading, boolean askDimension) {
		id = idCounter;
		idCounter++;
		this.name = name;
		this.descriptionL1 = descriptionL1;
		this.descriptionL2 = descriptionL2;
		this.askDimension = askDimension;
		this.askLoading = askLoading;
		try {
			this.image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			this.image = Toolkit.getDefaultToolkit().getImage(imagePath);
		}
	}

	/**
	* @return the template id
	*/
	public int getId() { return id; }
	/**
	* @return the template name
	*/
	public String getName() { return name; }
	/**
	* @return the template first line description
	*/
	public String getDescriptionL1() { return descriptionL1; }
	/**
	* @return the template second line description
	*/
	public String getDescriptionL2() { return descriptionL2; }
	/**
	* @return the template Image object
	*/
	public Image getImage() { return image; }
	/**
	* @return indicates if the template requires to input dimensions
	*/
	public boolean getAskDimension() { return askDimension; }
	/**
	* @return indicates if the template requires to load a savefile
	*/
	public boolean getAskLoading() { return askLoading; }
	/**
	* @param other the other template to check
	* @return true if the template given has the same id than this
	*/
	public boolean isEqual(NewWorldTemplate other) { return id == other.getId(); }	
}
