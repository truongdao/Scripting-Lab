package scriptlab.builtin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;

import javax.script.Invocable;
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
	 * create a new java object from a java interface and a js object by name.<p>
	 * @param - {@code jso_name} name of javascript object
	 * @param - {@code jv_classpath_str} class path of java interface/class
	 */
	public <T> Object convert(String jso_name, String jv_classpath_str){
		
		Class<T> inf =null;
		 ClassLoader classLoader = builtins.class.getClassLoader();
		 try {
		        inf = (Class<T>) classLoader.loadClass(jv_classpath_str);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		 
		Object obj = Main.engine.get(jso_name);
		Invocable inv = (Invocable) Main.engine;
		T act = inv.getInterface(obj, inf);
		
		return act;
	}
	 
	
	/**
	 * find a javascript variable by a string name. <p>
	 * provide user a method to access js variable with dynamic name.<p>
	 * @param - string which is name of new variable
	 */
	 public Object find(String var_name){

				return Main.engine.get(var_name);
	}
	 
	/**
	 * create javascript variable named by a string and valued by pre-defined obj. <p>
	 * provide user a method to create variable with dynamic name.<p>
	 * @param - {@code var_name} string which is name of new variable
	 * 			{@code ref_obj_name} string name of reference object
	 */
	 public void copy(String var_name, String ref_obj_name){
	 	try {
			Main.engine.eval("var "+var_name+" = "+ref_obj_name +";");
		} catch (ScriptException e) {
			Console.print(e.getMessage(), Console.MSGTYPE_ERROR);
		}
	 }
	 
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
	
	/**
	 * execute an javascript file based on its file path.
	 * @param js_path
	 * @return
	 */
	public static boolean eval(String js_path){
		try {
			
			js_path = lookUpRealFilePath(js_path);
			Main.engine.eval(new FileReader(js_path));
			
			return true;
		} catch (Exception e) {
			Console.print(e.getMessage(), Console.MSGTYPE_ERROR);
			return false;
		}
	}
	

	//-----------------------------------------------------------------------//
	
	/**
	 * Load an external jar library to program environment.
	 * @param jar_url
	 * @return
	 */
	public static boolean loadjar(String jar_url){
		
		jar_url = lookUpRealFilePath(jar_url);
		return EngineLoader.loadJar(jar_url);
		
	}
	
	//-----------------------------------------------------------------------//
	
	/**
	 * Load new java object based on its class, object must have constructor without parameters.<p>
	 * @param path_class - path to class inside jar file
	 * @return - loaded object
	 */
	public static Object loadclass(String  class_path) {
		
		return EngineLoader.loadClass(class_path);
	}

	//-----------------------------------------------------------------------//
	
	/**
	 * print a message to console
	 * @param msg
	 */
	public static void out(String msg) {

		Console.print(msg, Console.MSGTYPE_INFO);	
			
	}
	//-----------------------------------------------------------------------//
	
	/**
	 * print a message to console in new line
	 * @param msg
	 */
	public static void outln(String msg) {
		
		out(msg + "\n");
		
	}
	//-----------------------------------------------------------------------//
	
	/**
	 * clear console
	 */
	public static void clear() {
		
		Console.clear();
		
	}
	//-----------------------------------------------------------------------//
	
	static Dialog diag;
	static String content = "";
	static TextField txt;
	
	/**
	 * show dialog to get input string
	 * @param title - title for dialog 
	 * @param ivalue - initial value
	 * @return typed string
	 */
	public static String input(String title, String ivalue) {

		diag = new Dialog(Main.frame);
		diag.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		diag.setTitle(title);
		diag.setLayout(new FlowLayout(FlowLayout.CENTER));
		txt = new TextField(ivalue, 25);
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
	
	/**
	 * exit program immediately
	 */
	public static void exit() {
		
		System.exit(0);
		
	}
	////////////////// INTERNAL ROUTINEs //////////////////////////////////////
	
	/*
	 * return real path of the file name. it may be absolute path,
	 * cwd path or look up folder related path.
	 */
	private static String lookUpRealFilePath(String js_path){
		String fp = "";
		
		//if the file in current path
		File f = new File(js_path);
		if(f.exists()){
			fp = js_path;
		}
		
		//look up for the file in installed folder
		else{
			f = new File(Main.lookup_Path, js_path);
			if(f.exists()){
				fp =f.getAbsolutePath();
			}
		}
		return fp;
	}
}
