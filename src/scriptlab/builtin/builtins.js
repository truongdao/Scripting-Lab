
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
