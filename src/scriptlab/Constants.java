package scriptlab;

/**
 * Program constant storage.
 * @author pika
 *
 */
public class Constants {

	/**
	 * path to configuration script file which script is ran first.
	 */
	public static final String PATH_CONFIG_JS = "ScriptingLab.data/config.js";
	
	/**
	 * path to main program script file.
	 */
	public static final String PATH_MAIN_JS = "ScriptingLab.data/main.js";
	
	/**
	 * path to built-in functions initializer. The file is contained package,
	 * not file system.
	 */
	public static final  String PATH_BUILTINS_JS = "/scriptlab/builtin/builtins.js";
	
	/**
	 * referenced name of built-in {@code java object} in {@code javascript} engine.
	 */
	public static final String BUILTINS_NAME = "builtins";

	/**
	 * program & author information.
	 */
	public static final String ABOUT_PROGRAM =
			"Scripting Lab v1.2.1\n" +
			"Copyright (c) 2015 Truong Dao\n"+
			"Source distribution: https://github.com/truongdao";
	/**
	 * Guidelines for some built-in functions and tips which appear in console window. 		
	 */
	public static final String GUIDE_BUILTINS =
						"1. builtins: disableGUI(x), eval(x) , loadjar(x), loadclass(n,u,c), \n"
					+   "\t		out(x), outln(x), clear(), string input(), exit()\n"
					+	"2. Short key: Alt + Enter -> execute all\n"
					+	"\t 	Select text, Alt + Enter -> execute selected\n"
					+	"\t 	Shift + Enter -> execute working line\n";
}
