package scriptlab;

public class Constants {

	public static final String PATH_CONFIG_JS = "ScriptingLab.data/config.js";
	public static final String PATH_MAIN_JS = "ScriptingLab.data/main.js";
	public static final  String PATH_BUILTINS_JS = "/scriptlab/builtin/builtins.js";
	public static final String BUILTINS_NAME = "builtins";

	
	public static final String ABOUT_PROGRAM =
			"Scripting Lab v1.1.1\n" +
			"Copyright (c) 2015 Truong Dao\n"+
			"Source distribution: https://github.com/truongdao";
			
	public static final String GUIDE_BUILTINS =
						"1. builtins: disableGUI(x), eval(x) , loadjar(x), loadclass(n,u,c), \n"
					+   "\t		out(x), outln(x), clear(), string input(), exit()\n"
					+	"2. Short key: Alt + Enter -> execute all\n"
					+	"\t 	Select text, Alt + Enter -> execute selected\n"
					+	"\t 	Shift + Enter -> execute working line\n";
}
