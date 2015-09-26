package scriptlab.builtin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import scriptlab.Spider;


/******************************************************************************
 * Implement functionalities for builtins.						  
 * 																			  
 * @author tw81hc															  
 *																			  
 *****************************************************************************/
public class Builtins{
	


	private ScriptEngine engine = null;
	private Spider spider = null;

	public Builtins(Spider spi){
		this.spider = spi;
		engine = spi.common.engine;
	}
	
	////////////////INFORMATION DECLARING /////////////////////////////////////
	//////////////// OBJECTS UNDER CONTROL ////////////////////////////////////
	//////////////// FUNCTIONS EXPOSED ////////////////////////////////////////

	//-----------------------------------------------------------------------//
	
	/**
	 * print a message to console
	 * @param msg
	 */
	public void out(String msg) {
		System.out.println( msg);		
	}
	
	//-----------------------------------------------------------------------//
	/**
	 * Load a js source
	 * @param filePath
	 * @throws Exception 
	 */
	public void include(String filePath) throws Exception{
		InputStream is;
		try {
			
			String realpath = Builtins.lookUpRealFilePath(filePath, spider.config.lookupPaths);
			is = new FileInputStream(realpath);
			engine.eval(new InputStreamReader(is, "UTF8"));
			
		} catch (FileNotFoundException e) {
			System.err.println("#ERROR file not found! "+filePath);
		} catch (UnsupportedEncodingException | ScriptException ex) {
			System.err.println("#ERROR while running! "+filePath);
			System.err.println(ex.getMessage());
		}
		
	}
	//-----------------------------------------------------------------------//
	/**
	 * add jar library to JVM class path, from then packages can be imported .<p>
	 * 
	 * @param jar_url - path to jar file
	 * @return - {@code true} if load OK, {@code false} if fail
	 */
	public boolean loadjar(String jar_url) {
		try {
			
			String real_jar_url = lookUpRealFilePath(jar_url, spider.config.lookupPaths);
			
			File f = new File(real_jar_url);
			URI u = f.toURI();
			URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader
					.getSystemClassLoader();
			Class<URLClassLoader> urlClass = URLClassLoader.class;
			Method method;

			method = urlClass.getDeclaredMethod("addURL",
					new Class[] { URL.class });

			method.setAccessible(true);
			method.invoke(urlClassLoader, new Object[] { u.toURL() });
			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("#ERROR: Load jar file! "+ jar_url +"\n"+e.getMessage());
			return false;
		}
	}
	//-----------------------------------------------------------------------//

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
	public String input(String title, String ivalue) {

		diag = new Dialog(new Frame());
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
	public void exit() {
		
		System.exit(0);
		
	}

	/*****************************************************************************/
	/*    I N T E R N A L   R O U T I N E S                                      */
	/*****************************************************************************/

	
	/*
	 * return real path of the file name. it may be absolute path,
	 * cwd path or look up folder related path.
	 */
	public static String lookUpRealFilePath(String fpath, java.util.List<String> lookupPaths){
		String fp = "";
		
		//1. HIGH PRIORITY: if the file in current path
		File f = new File(fpath);
		if(f.exists()){
			fp = fpath;
		}
		
		//2. LOW PRIORITY: look up for the file in installed folder
		else{
			java.util.List<String> ss = lookupPaths;
			for(String s : ss){
				f = new File(s, fpath);
				if(f.exists()){
					fp =f.getAbsolutePath();
					break;
				}
			}
		}
		
		return fp;
	}
	
	/**
	 * due to some security reasons, j8 not allow to create engine in some cases
	 * this function may help to solve the issue. 
	 * this restrict function is not exposed to builtins.js, must access through 
	 * spider.x
	 * @Deprecated invoke new ScriptEngineManager(null).getEngineByName() to create new one
	 * @return
	 */
	@Deprecated
	public ScriptEngine create_new_engine(){
		return new ScriptEngineManager(null).
				getEngineByName(spider.config.engine_js_name);
	}

	/**
	 * create a new java object from a java interface and a js object by name.<p>
	 * new_java_object = (java_inf) current_js_obj;
	 * @param - {@code jso_name} name of javascript object
	 * @param - {@code jv_classpath_str} class path of java interface/class
	 */
	public <T> Object cast2Java(String jso_name, String jv_inf_name){
		
		Class<T> inf =null;
		 ClassLoader classLoader = Builtins.class.getClassLoader();
		 try {
		        inf = (Class<T>) classLoader.loadClass(jv_inf_name);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		 
		Object obj = engine.get(jso_name);
		Invocable inv = (Invocable) engine;
		T act = inv.getInterface(obj, inf);
		
		return act;
	}
}
