package scriptlab.builtin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.JTextPane;

import scriptlab.Constants;
import scriptlab.EngineLoader;
import scriptlab.Main;
import scriptlab.ScriptLabGUI;


/******************************************************************************
 * Implement functionalities for builtins.						  
 * 																			  
 * @author tw81hc															  
 *																			  
 *****************************************************************************/
public class builtins{
	


	public builtins(){
	}
	
	////////////////INFORMATION DECLARING /////////////////////////////////////
	//////////////// OBJECTS UNDER CONTROL ////////////////////////////////////
	//////////////// FUNCTIONS EXPOSED ////////////////////////////////////////
	
	/**
	 * display Window or not.<p>
	 * default is shown.
	 * @param - {@code true} is hidden, {@code false} is shown
	 */
	public static void disableGUI(boolean option){
		
		Main.frame_disabled = option;
		
		if(Main.frame !=null){
			Main.frame.setVisible(!option);
		}
	}
	
	public static boolean eval(String js_path){
		try {
			Main.engine.eval(new FileReader(js_path));
			return true;
		} catch (Exception e) {
			Console.print(e.getMessage(), Console.MSGTYPE_ERROR);
			return false;
		}
	}
	

	//-----------------------------------------------------------------------//
	
	public static boolean loadjar(String jar_url){
		
		return EngineLoader.loadJar(jar_url);
		
	}
	
	//-----------------------------------------------------------------------//
	
	public static boolean loadclass(String module_name, String jar_url, String class_path) {
		
		return EngineLoader.loadClass(Main.engine, module_name, jar_url, class_path);
	}

	//-----------------------------------------------------------------------//
	
	public static void out(String msg) {

		Console.print(msg, Console.MSGTYPE_INFO);	
			
	}
	//-----------------------------------------------------------------------//
	
	public static void outln(String msg) {
		
		out(msg + "\n");
		
	}
	//-----------------------------------------------------------------------//
	
	
	public static void clear() {
		
		Console.clear();
		
	}
	//-----------------------------------------------------------------------//
	
	static Dialog diag;
	static String content = "";
	static TextField txt;
	
	
	public static String input() {

		diag = new Dialog(Main.frame);
		diag.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		diag.setTitle("Get string");
		diag.setLayout(new FlowLayout(FlowLayout.CENTER));
		txt = new TextField("", 25);
		txt.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				content = txt.getText();
				if(content ==null) content = "";
				
				diag.setVisible(false);
			}
		});
		diag.setSize(240, 75);
		
		diag.add(txt);
		diag.setVisible(true);
		
		return content;
	}
	//-----------------------------------------------------------------------//
	
	
	public static void exit() {
		
		System.exit(0);
		
	}
	////////////////// INTERNAL ROUTINEs //////////////////////////////////////
	

}
