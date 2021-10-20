package gamelogic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
* The goal of the Saver class is to save and load a project's GameManager from a savefile.<br/><br/>
* 
* Saving a project is done in several steps.<br/>
* The first one is to create a temporary work folder.<br/>
* Then we serialize the GameManger into a txt file in this folder.<br/>
* After, another txt file is created to list all the images used by the surfaces. There is one line per Surface, with its name and a path to the image.<br/>
* If the pack image option is selected, the image will be copied in the temporary folder and a relative path is written.<br/>
* In the other case, it's an absolute path that is written. A lettre indicates if it's a relative or an absolute path.<br/>
* An image loaded from a save file with pack option will always be resaved as pack, as its absolute path has been lost.<br/>
* The same process occurs for the Species images.<br/>
* Once save is complete, the temporary folder is zipped and its extension is changed to .nws (but data speaking it's a .zip file).<br/>
* Finally the temporary folder is deleted.<br/><br/>
* 
* Loading follows the same principle:<br/>
* First unzip the savefile in a temporary folder.<br/>
* Then deserialize the GameManager.<br/>
* After that, for each line in the Surface Image List text file, retrieve the surface in the GameManager's Surfaces array.<br/>
* Load the image either with its absolute path or from the temporary folder (the letter indicating which option to choose for each surface) and set it to the retrieved surface.<br/>
* Do the same for species.<br/>
* Finally delete the temporary folder (the images are actually BufferedImage, so deleting their original file isn't an issue).<br/><br/><br/>
* 
* 
* WARNING -- If you plan on directly use the loaded GameManager, don't forget to init its transient attributes (for example its listeners) by calling initTransientFields().<br/><br/>
* 
* WARNING -- Because of the way the saving and loading processes works, if a folder already exists with the savefile name, it will get deleted.<br/><br/>
* 
* Zip and Unzip code partially use code written here: https://www.youtube.com/watch?v=lm1Y_vMzgQk&ab_channel=jinujawadm
* 
* @see GameManager
*/ 
public class Saver {


	// ===========	Save Methods	===========
	
	/**
	* Save a GameManager and all related images if asked.
	* @param saveFilePath
	* @param manager
	* @param packImages if true, all images used with an absolute path will be copied and zipped in the save file, otherwise their absolute path are saved
	*/ 
	public static void saveGame(String saveFilePath, GameManager manager, boolean packImages) {
		try {
			if (saveFileAlreadyExistsAndCancelSave(saveFilePath))
				return;
			
			String ext = saveFilePath.substring(saveFilePath.length() - 4);
			if (ext.equals(".nws"))
				saveFilePath = saveFilePath.substring(0, saveFilePath.length() - 4);
			
			ArrayList<File> tempSaveFiles = new ArrayList<>();
			File tempFolder = new File(saveFilePath);
			if (tempFolderAlreadyExists(tempFolder))
				return;
			
			
			

			// === Create and add the important text files at the begining of the list ===
			File managerFile = new File(saveFilePath + File.separator + "_manager.txt");
			tempSaveFiles.add(managerFile);
			File surfaceImageFile = new File(saveFilePath + File.separator + "_surfacesImages.txt");
			tempSaveFiles.add(surfaceImageFile);
			File speciesImageFile = new File(saveFilePath + File.separator + "_speciesImages.txt");
			tempSaveFiles.add(speciesImageFile);
	    	
	    	
			// === Save the gamemanager ===
			FileOutputStream managerFileOut = new FileOutputStream(managerFile);
			ObjectOutputStream objectOut = new ObjectOutputStream(managerFileOut);
	        objectOut.writeObject(manager);
	        objectOut.close();
	        System.out.println("The Manager was succesfully saved to a file");
	        

			// === Save the images used by the surfaces ===

			FileWriter surfaceImageOut = new FileWriter(surfaceImageFile);
			List<Surface> surfaces = manager.getSurfaceArray();
			for (int i = 0; i < surfaces.size(); i++) {
				saveImage(saveFilePath, packImages, tempSaveFiles, surfaceImageOut, surfaces.get(i));
			}
			surfaceImageOut.flush();
			surfaceImageOut.close();
	        System.out.println("All surfaces images have been saved !");
	        


			// === Save the images used by the species ===

			FileWriter speciesImageOut = new FileWriter(speciesImageFile);
			List<Species> species = manager.getSpeciesArray();
			for (int i = 0; i < species.size(); i++) {
				saveImage(saveFilePath, packImages, tempSaveFiles, speciesImageOut, species.get(i));
			}
			speciesImageOut.flush();
			speciesImageOut.close();
	        System.out.println("All surfaces images have been saved !");


			// === Zip the files into the save file ===
	        
	        zipSaveFiles(saveFilePath, tempSaveFiles);
	        

			// === Delete the files ===
	        
	        for(File file : tempSaveFiles) {
	        	file.delete();
				System.out.println("File Deleted: " + file);
	        }
	        tempFolder.delete();
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}        
	}
	
	/**
	* Save the image of an element.
	* @param saveFilePath
	* @param packImages if true, the image will be copied and zipped in the save file, otherwise its absolute path will be kept if not lost
	* @param imagesFiles the array of all the images File saved
	* @param imageOut the writer to the text file listing all saved images for the element class
	* @param element the current element to save the image of
	*/ 
	private static void saveImage(String saveFilePath, boolean packImages, ArrayList<File> imagesFiles, FileWriter imageOut, Element element) throws IOException {
		if (element.getImageFile() == null)
			return;
		BufferedImage bufferedImage = element.getImageFile().getBufferedImage();
		if (bufferedImage == null)
			return;
		
		String path = element.getImageFile().getPath();
		String lineToWrite;
		
		// Asks : do you want to save the image in the save file or is your image not linked to a real file (ie already loaded from a save file) ?
		if (packImages || path == null) {
			File currentImageFile = new File(saveFilePath + File.separator + "_images_" + element.toString() + ".png");
			ImageIO.write(bufferedImage, "png", currentImageFile);
			imagesFiles.add(currentImageFile);
			
			lineToWrite = element.toString() + "\ts\t" + currentImageFile.getName() + "\n";	// s indicates it's a save path
		} else
			lineToWrite = element.toString() + "\ta\t" + element.getImageFile().getPath() + "\n";	// a indicates it's an absolute path
		
		
		imageOut.write(lineToWrite);
        System.out.println("New image saved");
	}
	
	/**
	* Zip the temporary save folder into a .nws save file (that is data speaking a .zip file)
	* @param saveFilePath
	* @param imagesFiles the array of all the images File saved
	*/ 
	private static void zipSaveFiles(String zipFilePath, ArrayList<File> imagesFiles){
	     byte[] buffer = new byte[1024];
	     try {
	    	FileOutputStream fos = new FileOutputStream(zipFilePath + ".nws");
	    	ZipOutputStream zos = new ZipOutputStream(fos);
	    	System.out.println("Output to Zip : " + zipFilePath);
	    	for(File file : imagesFiles) {
				System.out.println("File Added");
				ZipEntry ze= new ZipEntry(file.getName());
				zos.putNextEntry(ze);
				FileInputStream in = new FileInputStream(file);
				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				in.close();
			}
			zos.closeEntry();
			zos.close();
			System.out.println("Zip Done");
	    } catch(IOException ex) {
	       ex.printStackTrace();
	    }
	}
	
	
	

	
	// ===========	Load Methods	===========
	
	
	/**
	* Load a GameManager and all related images.
	* @param saveFilePath
	* @return the loaded game manager
	*/ 
	public static GameManager loadGame(String saveFilePath) {
		File saveFile = new File(saveFilePath);
		if (!saveFile.exists()) {
			System.err.println("Save file doesn't exists !");
			return null;
		}
		
		ArrayList<File> files = new ArrayList<>();
		File folder = unzipSaveFiles(saveFilePath, files);
		if (folder == null) {
			System.err.println("Unzip of the save file failed");
			return null;			
		}
		
		GameManager manager = loadGameManager(files);
		if (manager == null)
			return null;		

		loadImages(manager, true, files);
		loadImages(manager, false, files);
		
		
		for(File file : files) {
        	file.delete();
			System.out.println("File Deleted: " + file);
        }
		folder.delete();
		
		
		Surface.synchIdCounter(manager.getSurfaceArray());
		Species.synchIdCounter(manager.getSpeciesArray());
		return manager;
	}
	
	/**
	* Loads a GameManager by deserializing it from its text file.
	* @param files all the files unzipped from the .nws savefile, including the GameManager Serialize text file
	* @return the loaded game manager, null if file not found or deserialisation went wrong
	*/ 
	private static GameManager loadGameManager(ArrayList<File> files) {
		int i = 0;
		File managerFile;
		do {
			managerFile = files.get(i);
			i++;
		}
		while (!managerFile.getName().equals("_manager.txt") && i < files.size());	// this while could theorically be skipped but is kept in case the user unzip & reziped his save file
		
		// Check that the manager file has been found
		if (i == files.size() && !managerFile.getName().equals("_manager.txt")) {
			System.err.println("Manager file not found.");
			return null;			
		}
		
		try (
			FileInputStream managerFileIn = new FileInputStream(managerFile);
		    ObjectInputStream objectIn = new ObjectInputStream(managerFileIn)) {
			return (GameManager) objectIn.readObject();
		} catch (IOException | ClassNotFoundException ex) {
		    ex.printStackTrace();
		    return null;
		}
	}
	
	/**
	* Loads all the images needed and set them to their corresponding element in the manager's list.
	* @param manager 
	* @param isSurface true if we're loading surface images, false if we're loading species images
	* @param files all the files unzipped from the .nws savefile, including the GameManager Serialize text file
	*/ 
	private static void loadImages(GameManager manager, boolean isSurface, ArrayList<File> files) {
		try {
			int i = 0;
			File imageListFile;
			String searchedFileName = isSurface ? "_surfacesImages.txt" : "_speciesImages.txt";
			
			do { 
				imageListFile = files.get(i);
				i++;
			}
			while (!imageListFile.getName().equals(searchedFileName) && i < files.size());
			
			if (i == files.size() && !imageListFile.getName().equals(searchedFileName)) {
				if (isSurface)
					System.err.println("Surface image list file not found.");
				else
					System.err.println("Species image list file not found.");
				return;
			}
			
			
			char[] buffer = new char[1024];
			FileReader imageIn = new FileReader(imageListFile);
			imageIn.read(buffer);
			imageIn.close();
			
			StringBuilder text = new StringBuilder();
			text.append(buffer);
			System.out.println(text.toString());
			
			String[] lines = text.toString().split("\n");
			for (String line : lines) {
				System.out.println(line);
				String[] cols = line.split("\t");
				
				if (cols.length != 3)
					continue;
				else if(cols[1].equals("a")) {		//absolute
					loadElementImage_AbsoluteExternal(getElementFromManager(manager, isSurface, cols[0]), cols[2]);
				} else if (cols[1].equals("s")) {	//saved image
					loadElementImage_Saved(getElementFromManager(manager, isSurface, cols[0]), cols[2], files);
				}					
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	* Loads all the images needed and set them to their corresponding element in the manager's list.
	* @param manager 
	* @param isSurface true if we're looking for a surface, false if we're looking for a species
	* @param elementName the name of the wanted element
	* @return the element wanted, if found
	*/ 
	private static Element getElementFromManager(GameManager manager, boolean isSurface, String elementName) {
		if (isSurface)
			return manager.getSurface(elementName);
		else
			return manager.getSpecies(elementName);
	}
	
	/**
	* Loads an external image using an absolute path and sets it as the element's image.
	* @param element the element receiving the image
	* @param imagePath the absolute path of the image file
	*/ 
	private static void loadElementImage_AbsoluteExternal(Element element, String imagePath) {
		if (element == null){
			System.err.println("Element is null -> Save file may be corrupted");
			return;
		}
		element.setImageFile(new ImageFile(imagePath));		
        System.out.println("Absolute Image loaded for element: " + element.toString() + "\t | " + imagePath);
	}

	/**
	* Loads an unzipped temporary image using the list of the unzipped files and sets it as the element's image.
	* @param element the element receiving the image
	* @param imageName the name of the image file
	* @param files the list of the unzipped files
	*/ 
	private static void loadElementImage_Saved(Element element, String imageName, ArrayList<File> files) {
		try {
			if (element == null){
				System.err.println("Element is null -> Save file may be corrupted");
				return;
			}
			
			File image = null;
			for (int i = 0; i < files.size(); i++) {
				if (files.get(i).getName().equals(imageName)) {
					image = files.get(i);
					break;
				}
			}
			
			if (image == null)
				return;			
		
			element.setImageFile(new ImageFile(ImageIO.read(image)));
	        System.out.println("Image loaded for element: " + element.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Unzips a .nws savefile into a temporary folder.
	* @param saveFilePath
	* @param files an empty list of files to fill with the unzipped files references
	*/ 
	private static File unzipSaveFiles(String saveFilePath, ArrayList<File> files) {
		System.out.println(saveFilePath);
		
		String ext = saveFilePath.substring(saveFilePath.length() - 4);
		System.out.println(ext);
		if (!ext.equals(".nws"))
			return null;
		
		byte[] buffer = new byte[1024];
		try{
			File folder = new File(saveFilePath.substring(0, saveFilePath.length() - 4));
			if (tempFolderAlreadyExists(folder))
				return null;
			
			System.out.println(folder);
			
			//get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(saveFilePath));
			//get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			while(ze != null){
			    String fileName = ze.getName();
			    File newFile = new File(folder + File.separator + fileName);
			    System.out.println("file unzip : "+ newFile.getAbsoluteFile());
			    //create all non exists folders
			    //else you will hit FileNotFoundException for compressed folder
			    new File(newFile.getParent()).mkdirs();
			    FileOutputStream fos = new FileOutputStream(newFile);
			    int len;
			    while ((len = zis.read(buffer)) > 0) {
			    	fos.write(buffer, 0, len);
			    }
			    fos.close();
			    ze = zis.getNextEntry();
			    files.add(newFile);
			}
			zis.closeEntry();
			zis.close();
			System.out.println("Unzip Done");
			
			return folder;
			
		} catch(IOException ex) {
	        ex.printStackTrace();
			return null;			
		}		
	}
	
	
	
	
	
	// ===========	File Already exists Checks	===========
	
	/**
	* Checks if a temporary folder already exists. If so, pops a an error dialog box. Else, create the temporary folder.
	* @param tempFolder the temporary folder to check
	* @return if the folder already exists or not (i.e. if should abort the operation or continue it)
	*/ 
	private static boolean tempFolderAlreadyExists(File tempFolder) {
		if (tempFolder.exists()) {
			System.err.println("Folder exists !!");
			JOptionPane.showMessageDialog(null, "Can't perform action on " + tempFolder.getAbsolutePath() + ".nws\nA folder with the same name already exists at this location.", "Error: Folder with same name exists", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		else {
	    	tempFolder.mkdir();
	    	return false;			
		}
	}
	
	/**
	* Checks if a save file already exists at specified location. If so, pops a dialog box to ask the user if it should be replaced.
	* @param saveFilePath the path of the save file to be
	* @return if the save should be abort or not
	*/ 
	private static boolean saveFileAlreadyExistsAndCancelSave(String filePath) {
		File saveFilee = new File(filePath);
		if (!saveFilee.exists())
			return false;
		
		System.out.println("Save file already exists");
		
		int res = JOptionPane.showConfirmDialog(null, filePath + " already exists.\nReplace it ?", "Save File already exists", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		return res != 0;	
	}
}