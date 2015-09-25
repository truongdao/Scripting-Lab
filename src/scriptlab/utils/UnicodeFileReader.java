package scriptlab.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;


public class UnicodeFileReader {

	public static String readFirstLine(String fileDir){
		
		String frstLine = null;
		try{
		BufferedReader in= new BufferedReader(
		           			new InputStreamReader(
		                      new FileInputStream(fileDir), "UTF8"));
		 frstLine = in.readLine(); 
		} catch(FileNotFoundException ex){
			System.err.println("#WARN file not found! "+fileDir);
		} catch(Exception e){
			frstLine = null;
			e.printStackTrace();
		}
		
		return frstLine;
	}
}
