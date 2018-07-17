package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {
	public String[] filename;
	public  HashMap<String,Integer> getFileName(String path) {
	   // String path = "D:/java/chatWeb/question"; // ·��
	    File f = new File(path);
	    if (!f.exists()) {
	      System.out.println(path + " not exists");
	      return null;
	    }
	    File fa[] = f.listFiles();
	    HashMap<String,Integer> filemap=new HashMap<String,Integer>();
	    for (int i = 0; i < fa.length; i++) {
	      File fs = fa[i];
	      if (fs.isDirectory()) {
	       // System.out.println(fs.getName() + " [Ŀ¼]");
	      } else {
	        //System.out.println(fs.getName());
	        if(fs.getName().indexOf("txt")>-1) {
	        	System.out.println(fs.getName()+"?????");
	        	filemap.put(fs.getName(),0);
	        }
	        
	      }
	    }
	    return filemap;
	  }
}
