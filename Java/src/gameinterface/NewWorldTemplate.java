package gameinterface;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NewWorldTemplate {
	public static NewWorldTemplate empty 					= new NewWorldTemplate("Empty", 			"A completely empty world.", "",								"res/_System_Template_Empty.png", true);
	public static NewWorldTemplate basic 					= new NewWorldTemplate("Basic", 			"An empty world but with", "already existing assets.", 			"res/_System_Template_Basic.png", true);
	public static NewWorldTemplate island 					= new NewWorldTemplate("Island", 			"A simple world with an", "island shaped terrain.",				"res/_System_Template_Island.png", true);
	public static NewWorldTemplate completeDemo				= new NewWorldTemplate("Demo", 				"A complete demo world to", "use as example.",					"res/_System_Template_Demo.png", false);
	
	public static NewWorldTemplate loadElements 			= new NewWorldTemplate("Import Data", 		"Load surfaces & species", "from a save file.", 				"res/_System_Template_LoadElements.png", true);
	public static NewWorldTemplate loadElementsTerrain 		= new NewWorldTemplate("Import Terrain",	"Load surfaces, species &", "terrain from a save file.",		"res/_System_Template_LoadEleTerrain.png", false);
	public static NewWorldTemplate loadElementsAllNodes 	= new NewWorldTemplate("Import Graphs", 	"Load surfaces, species &", "all graphs from a save file.", 	"res/_System_Template_LoadEleTerGraphs.png", false);
	public static NewWorldTemplate loadFullSave 			= new NewWorldTemplate("Complete Load", 	"Resume from a save file.", "", 								"res/_System_Template_LoadFull.png", false);
	
	// ====================================================
	
	
	private static int idCounter = 0;
	
	private int id;
	private String name;
	private String descriptionL1;
	private String descriptionL2;
	private Image image;
	private boolean askDimension;
	
	public NewWorldTemplate(String name, String descriptionL1, String descriptionL2, String imagePath, boolean askDimension) {
		id = idCounter;
		idCounter++;
		this.name = name;
		this.descriptionL1 = descriptionL1;
		this.descriptionL2 = descriptionL2;
		this.askDimension = askDimension;
		try {
			this.image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			this.image = Toolkit.getDefaultToolkit().getImage(imagePath);
		}
	}

	public int getId() { return id; }
	public String getName() { return name; }
	public String getDescriptionL1() { return descriptionL1; }
	public String getDescriptionL2() { return descriptionL2; }
	public Image getImage() { return image; }
	public boolean getAskDimension() { return askDimension; }
	public boolean isEqual(NewWorldTemplate other) { return id == other.getId(); }	
}
