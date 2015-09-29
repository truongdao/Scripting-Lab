package scriptlab;

import java.awt.EventQueue;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import scriptlab.CodeTracker.Record;
import scriptlab.builtin.Builtins;
import scriptlab.plugin.SpiderIde;
import scriptlab.utils.UnicodeFileReader;
import scriptlab.utils.UnixCmdProcessor;

/**
 * Entrance of program, which initializes script engine, gui...
 * and also contains reference to components.
 * @author pika
 *
 */
public class Main {

	/////////////////////// COMMON DATA //////////////////////////////////
	

	public static  String lookup_Path = "";	
	
	 /////////////////////// COMMON DATA //////////////////////////////////
	static String executing_file = null;
	public static Spider spider = new Spider();
	
	/**
	 * Launch the application.<p>
	 * @param commands
	 */
	public static void main(String...args){
		
		coreInit(args);
		featureLoad();
		if(spider.config.enable_ide){
			loadIDE(spider);
		} else{
			executeScript();
		}
		
	}



	/*****************************************************************************/
	/*    L E V E L   1                                                          */
	/*****************************************************************************/


	private static void coreInit(String[] args) {
		
		//1. parse Cmd string
		UnixCmdProcessor ucmd =  UnixCmdProcessor.parseGroup(args);
		
		//2.0 default configs
		spider.config.enable_ide = false;

		//2. map command arguments to spider.config.* 
		handleInvocationCommand(ucmd);
		
		//3. repeat setting flag enable_ide from other config sources
		if(!ucmd.arguments.containsKey("ide")){
			
			//3a. LOW PRIORITY: handle direction in first line of the config.js file.
			{
				String realPath = Builtins.lookUpRealFilePath(
						spider.constant.PATH_CONFIG_JS, 
						spider.config.lookupPaths);
				String firstLine = UnicodeFileReader.readFirstLine(realPath);

				if(firstLine!=null){
					if(firstLine.startsWith(spider.constant.ENABLE_GUI_INLINE_DIRECTION))
						spider.config.enable_ide = true;
					else if(firstLine.startsWith(spider.constant.DISABLE_GUI_INLINE_DIRECTION))
						spider.config.enable_ide = false;
				}
			}
			
			//3b. HIGH PRIORITY: handle direction in first line of the jsx file.
			if(ucmd.objects.size()>0){
				String jsxFile = ucmd.objects.get(0);
				String realPath = Builtins.lookUpRealFilePath(
						jsxFile, 
						spider.config.lookupPaths);
				String firstLine = UnicodeFileReader.readFirstLine(realPath);

				if(firstLine!=null){
					if(firstLine.startsWith(spider.constant.ENABLE_GUI_INLINE_DIRECTION))
						spider.config.enable_ide = true;
					else if(firstLine.startsWith(spider.constant.DISABLE_GUI_INLINE_DIRECTION))
						spider.config.enable_ide = false;
				}
			}
		}
		
		//4. Init engine
		if(spider.config.enable_ide){

			spider.common.code_tracker = new CodeTracker();
			spider.common.engine = new ScriptEngineWrapper(
					spider.config.engine_js_name,
					spider.common.code_tracker,
					spider.config.NASHORN_DEFAULT_OPTIONS
					);
		} else {
			if("Nashorn".matches(spider.config.engine_js_name))	{
				spider.common.engine = 
					new NashornLoadingSupporter().getNashornScriptEngine(spider.config.NASHORN_DEFAULT_OPTIONS);
			}
			else
			{
				spider.common.engine = 
						new ScriptEngineManager(null).getEngineByName(spider.config.engine_js_name);
			}
			spider.common.code_tracker = null;
		}
		
		ScriptEngine engine = spider.common.engine;
		
		
		//4b. put spider data to global space of js engine
		
			// then it should be mapped to spider.x object
			//Java(Spider) -> js(spider.x)
			engine.put("__SPIDER_GLOBAL__", spider);
			
		
		//5. load built-ins
			
			//Java(Spider) -> js(spider.x)
			spider.builtins = new Builtins(spider);	
			
			String filePath = spider.constant.PATH_BUILTINS_JS;
			try {
				
				InputStream is;
				is = Main.class.getResourceAsStream(filePath);
				engine.eval(new InputStreamReader(is, "UTF8"));
			
			} catch (NullPointerException e) {
				System.err.println("#ERROR file not found! "+filePath);
			} catch (UnsupportedEncodingException | ScriptException ex) {
				System.err.println("#ERROR while loading! "+filePath);
				System.err.println(ex.getMessage());
			}
			
	}

	private static void featureLoad() {
		 String item = spider.constant.PATH_CONFIG_JS;
		 try {
			 spider.builtins.include(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void executeScript() {
		String item = "";
		try{
			for (String it : spider.common.programs) {
				item = it;
				spider.builtins.include(item);
			}
		} catch(Exception e){
			return;
		}
	}
	

	private static void loadIDE(Spider sp) {
	
		Class<?> classToLoad;
		try {
			classToLoad = Class.forName (sp.config.ide);
			SpiderIde instance = (SpiderIde) classToLoad.getConstructor(Spider.class).newInstance (sp);
			instance.init();
		} catch (Exception e) {
			System.err.println("#ERROR: initialization GUI! " + sp.config.ide);
			e.printStackTrace();
		}
		
	}

	/*****************************************************************************/
	/*    L E V E L   2                                                          */
	/*****************************************************************************/

	private static void handleInvocationCommand(UnixCmdProcessor ucmd) {
				
		spider.config.engine_js_name = 
				!ucmd.arguments.containsKey("engine")? 
						System.getProperty("java.version").startsWith("1.8")?
						"Nashorn" :							//^-engine & jre ver = 1.8
						"JavaScript":							//^-engine & jre ver= ?,rhino
						ucmd.arguments.get("engine") ; 		//-engine=abc
						
		spider.config.enable_ide = 
				ucmd.arguments.containsKey("ide")? 
						"no".equals(ucmd.arguments.get("ide"))?
								false:			//-ide=no
									true:		//-ide=[^no]
										spider.config.enable_ide; 	//^-ide, check in code
		
		String strPaths = ucmd.arguments.get("paths");
		List<String> ls1 = new LinkedList<String>();
		//2nd processing
		if(strPaths !=null)
			ls1 = UnixCmdProcessor.seperateListObjects(strPaths, ";");
		
		
		spider.config.lookupPaths.addAll(ls1);
	
		spider.common.programs.addAll(ucmd.objects);
		
	}
	
	/*****************************************************************************/
	/*    I N T E R N A L   R O U T I N E S                                      */
	/*****************************************************************************/

	
	
}
