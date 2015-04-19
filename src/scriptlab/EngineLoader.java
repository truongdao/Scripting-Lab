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
	 * Load new module to JavaScript engine, a module in JS is respective to a class in Java.<p>
	 * This function, originally should be used by script file.<p><p>
	 * 
	 * Example: {@code load(jse, "foo", "d:\LibFoo.jar", "org.foo.Foo");}
	 * 
	 * @param jse - JS engine loaded to
	 * @param module_name - object name in JS engine
	 * @param jar_url - path to jar file
	 * @param class_path - path to class inside jar file
	 * @return - {@code true} if load OK, {@code false} if fail
	 */
	public static boolean loadClass(ScriptEngine jse, String module_name, String jar_url, String class_path){
		/*
		 * Load jar -> load class -> put to engine
		 */
		try {
			
			//1. Load jar file
			ClassLoader cl;
			File file = new File(jar_url);
			URL url = file.toURI().toURL();
			URL[] urls = new URL[]{url};
			cl = new URLClassLoader(urls);
			
			//2. Load class as object
			Object obj;
			Class cls = cl.loadClass(class_path);
			obj = cls.newInstance();

			//3. Put the class to engine and name it
			jse.put(module_name, obj);
			
			return true;
		} catch (Exception e1) {
			 Console.print("\n"+e1.getMessage(), Console.MSGTYPE_ERROR);
			 return false;
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


