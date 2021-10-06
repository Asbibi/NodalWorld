package gamelogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Saver {

	public static void SaveGame(String saveFilePath, GameManager manager) {
		File saveFile = new File(saveFilePath + ".nws");
		try {
			FileOutputStream fileOut = new FileOutputStream(saveFile);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
	        objectOut.writeObject(manager);
	        objectOut.close();
	        System.out.println("The Manager  was succesfully saved to a file");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public static void test() {}
	
	
	public static void LoadGame() {
		
	}
	
}

/* ===== ZIP A FOLDER (SAVE) =====
 
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
public class Zip_Folder {
    List fileList;
    private static final String OUTPUT_ZIP_FILE = "E:\\java\\zip_complete_folder.zip";
    private static final String SOURCE_FOLDER = "E:\\java\\zip_complete_folder";
    
    
    Zip_Folder(){
		fileList = new ArrayList();
    }
    
    public static void main( String[] args )
    {
    	Zip_Folder appZip = new Zip_Folder();
    	appZip.generateFileList(new File(SOURCE_FOLDER));
    	appZip.zipIt(OUTPUT_ZIP_FILE);
    }
    
    
     * Zip it
     * @param zipFile output ZIP file location  
     *   
    public void zipIt(String zipFile){
     byte[] buffer = new byte[1024];
     try{
    	FileOutputStream fos = new FileOutputStream(zipFile);
    	ZipOutputStream zos = new ZipOutputStream(fos);
    	System.out.println("Output to Zip : " + zipFile);
    	for(String file : this.fileList){
    		System.out.println("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);
        	FileInputStream in =
                       new FileInputStream(SOURCE_FOLDER + File.separator + file);
        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}
        	in.close();
    	}
    	zos.closeEntry();
    	//remember close it
    	zos.close();
    	System.out.println("Done");
    }catch(IOException ex){
       ex.printStackTrace();
    }
   }


     * Traverse a directory and get all files,
     * and add the file into fileList
     * @param node file or directory
     *
    public void generateFileList(File node){
    	//add file only
		if(node.isFile()){
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				generateFileList(new File(node, filename));
			}
		}
    }


     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     *
    private String generateZipEntry(String file){
    	return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
}
*/
/* ===== ZIP A FOLDER (LOAD) =====
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
public class Unzip_File_Folder {
    List fileList;
    private static final String INPUT_ZIP_FILE = "E:\\java\\testing.zip";
    private static final String OUTPUT_FOLDER = "E:\\java\\UNZIPPED_file";
    public static void main( String[] args )
    {
    	Unzip_File_Folder unZip = new Unzip_File_Folder();
    	unZip.unZipIt(INPUT_ZIP_FILE,OUTPUT_FOLDER);
    }
    
    
     * Unzip it
     * @param zipFile input zip file
     * @param output zip file output folder
     
    public void unZipIt(String zipFile, String outputFolder){
     byte[] buffer = new byte[1024];
     try{
    	//create output directory is not exists
    	File folder = new File(OUTPUT_FOLDER);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
    	//get the zip file content
    	ZipInputStream zis =
    		new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
    	while(ze!=null){
    	   String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);
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
    	}
        zis.closeEntry();
    	zis.close();
    	System.out.println("Done");
    }catch(IOException ex){
       ex.printStackTrace();
    }
   }
}
*/