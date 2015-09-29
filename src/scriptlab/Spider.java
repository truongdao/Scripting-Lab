package scriptlab;

import java.util.LinkedList;
import java.util.List;

import javax.script.ScriptEngine;

import scriptlab.builtin.Builtins;

/**
 * Program constant storage.
 * Do not use static attribute for properties, script engine cannot access!
 * @author pika
 *
 */
public class Spider {

	public static class ConstantClass{
		/**
		 * path to configuration script file which script is ran first.
		 */
		public final String PATH_CONFIG_JS = "spider.lib/config.js";
		
		/**
		 * path to main program script file.
		 */
		public final String PATH_MAIN_JS = "spider.lib/main.js";
		
		/**
		 * path to built-in functions initializer. The file is contained package,
		 * not file system.
		 */
		public final  String PATH_BUILTINS_JS = "/scriptlab/builtin/builtins.js";
	
		/**
		 * program & author information.
		 */
		public final String ABOUT_PROGRAM =
				"Spider v2 (old Scripting Lab)\n" +
				"Copyright (c) 2015 Truong Dao\n"+
				"Source distribution: https://github.com/truongdao";
		/**
		 * Guidelines for some built-in functions and tips which appear in console window. 	
		 * TODO	
		 */
		public final String GUIDE_BUILTINS =
							"1. builtins: disableGUI(x), eval(x) , loadjar(x), loadclass(n,u,c), \n"
						+   "\t		out(x), outln(x), clear(), string input(), exit()\n"
						+	"2. Short key: Alt + Enter -> execute all\n"
						+	"\t 	Select text, Alt + Enter -> execute selected\n"
						+	"\t 	Shift + Enter -> execute working line\n";
		
		public final String ENABLE_GUI_INLINE_DIRECTION = "/*spider.x.constant.enable_ide=true*/";
		public final String DISABLE_GUI_INLINE_DIRECTION = "/*spider.x.constant.enable_ide=false*/";
	};
	
	public static class ConfigClass{
		public boolean enable_ide = false;
		public String	ide="";	//classpath to IDE main  
		public String engine_js_name = "Nashorn"; //'JavaScript' for Rhino j1.7
		public List<String> lookupPaths = new LinkedList<String>();
		public final String[] NASHORN_DEFAULT_OPTIONS = new String[] { "-scripting", "-doe" };
		
	};

	public static class CommonClass{
		public List<String> programs = new LinkedList<String>();
		
		//main engine program running on
		public ScriptEngine engine = null;
		public CodeTracker code_tracker = null;
		
	};
	
	public ConstantClass constant  = new ConstantClass();
	public ConfigClass config = new ConfigClass();
	public CommonClass common = new CommonClass();
	public Builtins builtins;

}
