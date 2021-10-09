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

public class Saver {

	// ===========	Save Methods	===========
	
	public static void saveGame(String saveFilePath, GameManager manager, boolean packImages) {
		try {
			String ext = saveFilePath.substring(saveFilePath.length() - 4);
			if (ext.equals(".nws"))
				saveFilePath = saveFilePath.substring(0, saveFilePath.length() - 4);
			
			ArrayList<File> tempSaveFiles = new ArrayList<>();
			File tempFolder = new File(saveFilePath);
	    	tempFolder.mkdir();
			

			// === Create and add the important text files at the begining of the list ===
			File managerFile = new File(saveFilePath + "\\_manager.txt");
			tempSaveFiles.add(managerFile);
			File surfaceImageFile = new File(saveFilePath + "\\_surfacesImages.txt");
			tempSaveFiles.add(surfaceImageFile);
			File speciesImageFile = new File(saveFilePath + "\\_speciesImages.txt");
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
			File currentImageFile = new File(saveFilePath + "\\_images_" + element.toString() + ".png");
			ImageIO.write(bufferedImage, "png", currentImageFile);
			imagesFiles.add(currentImageFile);
			
			lineToWrite = element.toString() + "\ts\t" + currentImageFile.getName() + "\n";	// s indicates it's a save path
		} else
			lineToWrite = element.toString() + "\ta\t" + element.getImageFile().getPath() + "\n";	// a indicates it's an absolute path
		
		
		imageOut.write(lineToWrite);
        System.out.println("New image saved");
	}
	
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
		
		return manager;
	}
	
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
	private static Element getElementFromManager(GameManager manager, boolean isSurface, String elementName) {
		if (isSurface)
			return manager.getSurface(elementName);
		else
			return manager.getSpecies(elementName);
	}
	private static void loadElementImage_AbsoluteExternal(Element element, String imagePath) {
		if (element == null){
			System.err.println("Element is null -> Save file may be corrupted");
			return;
		}
		element.setImageFile(new ImageFile(imagePath));		
        System.out.println("Absolute Image loaded for element: " + element.toString() + "\t | " + imagePath);
	}
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
	
	private static File unzipSaveFiles(String saveFilePath, ArrayList<File> files) {
		System.out.println(saveFilePath);
		
		String ext = saveFilePath.substring(saveFilePath.length() - 4);
		System.out.println(ext);
		if (!ext.equals(".nws"))
			return null;
		
		byte[] buffer = new byte[1024];
		try{
			File folder = new File(saveFilePath.substring(0, saveFilePath.length() - 4));
			folder.mkdir();
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
}