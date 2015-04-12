package scriptlab.builtin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
