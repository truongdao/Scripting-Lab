package scriptlab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import scriptlab.builtin.Console;
import scriptlab.builtin.builtins;

/**
 * Library for initializing an engine.<p>
 * @author pika
 *
 */
public class EngineLoader{
	
	private static builtins obj_builtins = new builtins();
	
	/**
	 * Initialize a JavaScript Engine to be suitable for ScriptingLab.<p> 
	 * @param jse - JavaScript Engine
	 * @return - {@code true} if load OK, {@code false} if fail
	 */
	public static boolean initEngine(ScriptEngine jse){
		/* 
		 * load built-ins
		 */
		
		//1. load built-ins
		String module_name = Constants.BUILTINS_NAME;
		jse.put(module_name, obj_builtins);
		
		try {
//			jse.eval(new FileReader(Constants.PATH_BUILTINS_JS));
			InputStream is = 	EngineLoader.class.getResourceAsStream(Constants.PATH_BUILTINS_JS);
			jse.eval(new InputStreamReader(is));
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	/**
	  * Load new java object based on its class.
	  * object must have constructor without parameters and unpackaged.<p>
	 * @param class_path - path to class inside jar file
	 * @return - loaded object
	 */
	public static Object loadClass(String  class_path){

		try {
			
			Object obj;
			File f = new File(class_path);
			String clzz = f.getName().replaceFirst("[.][^.]+$", "");;

			URL urls[] = new URL[]{f.getParentFile().toURI().toURL()};
			obj = new URLClassLoader(urls).loadClass(clzz).newInstance();
			
			return obj;
		} catch (Exception e1) {
			e1.printStackTrace();
			 Console.print("\n"+e1.getMessage(), Console.MSGTYPE_ERROR);
			 return null;
		}
	
	}

	/**
	 * add jar library to JVM class path, from then packages can be imported .<p>
	 * 
	 * @param jar_url - path to jar file
	 * @return - {@code true} if load OK, {@code false} if fail
	 */
	public static boolean loadJar(String jar_url) {
		try {
			File f = new File(jar_url);
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
			Console.print("\nERROR: Load jar file "+e.getMessage(), Console.MSGTYPE_ERROR);
			return false;
		}
	}
	
}


