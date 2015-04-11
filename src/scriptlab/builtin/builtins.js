
	/**
	 * create a new java object from a java interface and a js object by name.<p>
	 * @param - {@code jso_name} name of javascript object
	 * @param - {@code jv_classpath_str} class path of java interface/class
	 */
	 function convert(jso_name, jv_classpath_str){
	 	return builtins.convert(jso_name, jv_classpath_str);
	 }
	 
	/**
	 * find a javascript variable by a string name. <p>
	 * provide user a method to access js variable with dynamic name.<p>
	 * @param - string which is name of new variable
	 */
	 function find(var_name){
	 	return builtins.find(var_name);
	 }
	 
	/**
	 * create javascript variable named by a string and valued by pre-defined obj. <p>
	 * provide user a method to create variable with dynamic name.<p>
	 * @param - {@code var_name} string which is name of new variable
	 * 			{@code ref_obj_name} string name of reference object
	 */
	 function copy(var_name, ref_obj_name){
	 	builtins.copy(var_name, ref_obj_name);
	 }
	 
	/**
	 * display Window or not.<p>
	 * default is shown.
	 * @param - {@code true} is hidden, {@code false} is shown
	 */
	function disableGUI(option){
		builtins.disableGUI(option);
	}
	
	/**
	 * load & execute an external .js library
	 * @param js_path - path to .js file
	 * @return {@code true} if load OK, {@code false} if fail
	 */
	function eval(js_path){
		return builtins.eval(js_path);
	}

	/**
	 * add jar library to JVM class path, from then packages can be imported .<p>
	 * 
	 * @param jar_url - path to jar file
	 * @return - {@code true} if load OK, {@code false} if fail
	 */
	function loadjar(jar_url){
		return builtins.loadjar(jar_url);
	}
		
	/**
	 * Load new module to JavaScript engine, a module in JS is respective to a class in Java.<p>
	 * @param module_name - object name in JS engine
	 * @param jar_url - path to jar file
	 * @param class_path - path to class inside jar file
	 * @return - {@code true} if load OK, {@code false} if fail
	 */
	function loadclass(module_name, jar_url, class_path){
		return builtins.loadclass(module_name, jar_url, class_path);
	}
	
	/**
	 * print string to console or SL or JVM
	 * @param msg - Message to be shown on console
	 */
	function out(msg){
		builtins.out(msg);
	}
	
	/**
	 * print string and new line to console or SL or JVM
	 * @param msg - Message to be shown on console
	 */
	function outln(msg){
		builtins.outln(msg);
	}
	
	/**
	 * clear SL console
	 */
	function clear (){
		builtins.clear();
	}
	
	/**
	 * show query box to get string data
	 * @return string typed
	 */
	function input(){
		return builtins.input();
	}	
	
	/**
	 * terminate SL
	 */
	function exit(){
		builtins.exit();
	};	
