import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import static jdk.nashorn.internal.objects.NativeError.getStackTrace;

public class Driver{
        private static class Files{
            public final LinkedList<String> pathList;
            public final String destination;
            public Files(LinkedList<String> pathList, File des){
                this.pathList = pathList;
                this.destination = des.getPath();
            }
        }
	public static void main(String[] args) throws InterruptedException{

            Files paths; //used to store all paths of files and destinations;
            String[] musicEx = {".mp3",".flac",".3ga",".cda",".mv3",".ogg",".mp4a",".aac",".wma",".ape",".alac"};
            String[] picEx = {".tiff",".tif",".gif",".jpeg",".jpg",".jif",".jfif",".jp2",".jpx",".j2k",".fpx",".png",".webm"};
            String[] vidEx = {".avi", ".asf", ".mov", ".mp4",".flv", ".flv",".mpg",".wmv", ".h264", ".DivX",".Xvid"};
            String[] officeEx = {".ooml", ".doc", ".docm", ".docx", ".docb", ".dotx",".xls", ".xlm", ".xlt", ".xlsx", ".xlsm", "xltx", ".xltm", ".xlsb", ".xla", ".xlw", ".ppt", ".pps", ".pptx", ".ppt,", ".potx",".potm", "ppsx", ".ppsm", ".sldx", ".sldm"};
           
            ArrayList<String> customTemp = new ArrayList<String>();
            
    
            boolean quit = false;
            
            
            Scanner scan = new Scanner(System.in);
            for (int i=0; i<200; i++){
                System.out.println();
            }
            System.out.println("Welcome to Backup Utility");
            System.out.println("Choose one of the following operations by entering provided letter:");
            System.out.println("a - To backup music");
            System.out.println("b - To backup pictures");
            System.out.println("c - To backup videos");
            System.out.println("d - To backup Microsoft Office Documents");
            System.out.println("e - Enter specific file types to backup");
            System.out.println("f - View directory information");
            System.out.println("q - To quit");
            
            
            while(quit == false){
             try{
                 switch(scan.nextLine().toLowerCase().charAt(0)){
                     
                    case 'a':
                         System.out.println("Now choose the directory that you want to backup the music from");
                         paths = locate(musicEx);
                         musicBackup(paths.pathList, paths.destination);
                         System.out.println("Backup completed successfully");
                         break;
                         
                    case 'b':
                         System.out.println("Now choose the directory that you want to backup the pictures from");
                         paths = locate(picEx);
                         otherBackup(paths.pathList, paths.destination);
                         System.out.println("Backup completed successfully");
                         break;
                        
                    case 'c':
                         System.out.println("Now choose the directory that you want to backup the videos from");
                         paths = locate(vidEx);
                         otherBackup(paths.pathList, paths.destination);
                         System.out.println("Backup completed successfully");
                         break;
                         
                    case 'd':
                        System.out.println("Now choose the directory that you want to backup the documents from");
                         paths = locate(officeEx);
                         otherBackup(paths.pathList, paths.destination);
                         System.out.println("Backup completed successfully");
                         break;
                         
                    case 'e':
                        System.out.println("Now enter the file types you want to backup seperated by a space and hit enter when your done");
                        System.out.println("Example: .pdf .zip .7z");
                        customTemp.clear();
                        while (!scan.hasNextLine()){
                            customTemp.add(scan.next());
                        }
                       
                        paths = locate((String[])customTemp.toArray());
                        otherBackup(paths.pathList, paths.destination);
                        System.out.println("Backup completed  successfully");
                        break;
                        
                    case 'f':
                        System.out.println("Select the directory you want to find file numbers in");
                        Thread.sleep(1000);
                        String dir = directoryChooser().getPath();
                        System.out.println(dir+" & its subdirectories have "+fileList(dir,musicEx).size()+" music files in them");
                        System.out.println(dir+" & its subdirectories have "+fileList(dir,picEx).size()+" pictures in them");
                        System.out.println(dir+" & its subdirectories have "+fileList(dir,vidEx).size()+" videos in them");
                        System.out.println(dir+" & its subdirectories have "+fileList(dir,officeEx).size()+" documents in them");
                        break;
                        
                    case 'q':
                        System.out.println("Quitting");
                        quit = true;
                        break;
                        
                    default:
                        System.out.println("ERROR: Invalid Choice");
                        break;
                         
                 }
                 
             }
             catch(StringIndexOutOfBoundsException e){
                 System.out.println("Invalid input try again");
             }

            }
	}
        
        public static Files locate(String[] fileList) throws InterruptedException{
            
                         Thread.sleep(1000);
                         File dir = directoryChooser();
                         System.out.println(dir.getPath());
                         System.out.println("Choose the directory you want to backup your files to");
                         Thread.sleep(1000);
                         File destination = directoryChooser();
                         
                         LinkedList<String> files = fileList(dir.getPath(), fileList);
                         System.out.println(files.size()+" files found");
                         return new Files(files,destination);
                                         
        }
        
        public static File directoryChooser(){
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                 System.out.println("You chose to open this file: " +
                 chooser.getSelectedFile().getName());
            }
            return chooser.getSelectedFile();
        }
        //returns a list of file paths in a directory from an array of file extensions
	static LinkedList<String> fileList(String directory, String[] types) {
		  LinkedList<String> textFiles = new LinkedList<String>();
		  File dir = new File(directory);
		  for (File file : dir.listFiles()) {
                        //System.out.println("file "+file.getName());
                        if (file.isDirectory() == true){
                            LinkedList<String> temp = fileList(file.toString(), types);
                            for (int i=0; i<temp.size(); i++){
                                textFiles.add(temp.get(i));    
                            }
                        }
                        else{
                            for (int i=0; i<types.length;i++){
                            //System.out.println
                       	    if (file.getName().toLowerCase().endsWith((types[i]))) {
		      		textFiles.add(file.getAbsolutePath());
                            
                            }        
                            }
			}
		  }
		  return textFiles;
	}
        //takes an array of music file paths and copies it over to the finalpath
        static void musicBackup(LinkedList<String> ogPath, String finalPath){
            System.out.println("Copying music to "+finalPath);
            File[] folderFile = new File(finalPath).listFiles(File::isDirectory);
            ArrayList<String> directories = new ArrayList<String>();
            for (int i=0; i<folderFile.length; i++){
                directories.add(folderFile[i].getName());
            }     
            for (int i=0; i<ogPath.size(); i++){
                String artist = "Unknown Artist";
                
                //try to organize by artist
                try {

                    Mp3File song  = new Mp3File(ogPath.get(i));

                    ID3v1 tag = song.getId3v1Tag();

                    artist = tag.getArtist();
                    
                    System.out.println(artist);
                }
                catch(Exception e){
                    //System.out.println(getStackTrace(e));
                    //artist = "Unknown Artist";
                }
                    //need to create a new directory for the artist
                    if (!Arrays.asList(directories).contains(artist)){
                        new File(finalPath+"\\"+artist).mkdir();
                        directories.add(artist);
                    }
                    File temp1 = new File(ogPath.get(i));
                    File temp2 = new File(finalPath+"\\"+artist+"\\"+temp1.getName());
                    
                try {
                    java.nio.file.Files.copy(temp1.toPath(), temp2.toPath());
                    System.out.println("Copied "+i+" of "+ogPath.size()+" files");
                    // copyFile(ogPath[i], finalPath+"\\"+artist);     
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                }
             

            }
        //takes a array of paths and copies to finalPath organizing by parent directories name
        static void otherBackup(LinkedList<String> ogPath, String finalPath){
            File[] folderFile = new File(finalPath).listFiles(File::isDirectory);
            ArrayList<String> directories = new ArrayList<String>();
            
            for (int i=0; i<ogPath.size(); i++){
                directories.add(folderFile[i].getName());
            }
            
            for (int i=0; i<ogPath.size(); i++){
                
                File original = new File(ogPath.get(i));
                File parent = original.getParentFile();
                if (!Arrays.asList(directories).contains(parent.getName())){
                    new File(finalPath+"\\"+parent.getName()).mkdir();
                    directories.add(parent.getName());
                    
                   
                }
                File temp = new File(finalPath+"\\"+parent.getName());
                try {
                    java.nio.file.Files.copy(original.toPath(), temp.toPath()); 
               
                            } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            
            
            
        }
        
        
        

        
      
}