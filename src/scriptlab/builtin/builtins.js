
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
	 * Load new java object based on its class, object must have constructor without parameters.<p>
	 * @param path_class - path to class inside jar file
	 * @return - loaded object
	 */
	function loadclass(path_class){
		return builtins.loadclass(path_class);
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
	
	var __previous_input_ivalue = '';
	/**
	 * show dialog to get input string. Previous value feature added.
	 * @param title - title for dialog 
	 * @param ivalue - initial value
	 * @return typed string
	 */
	function input(title, ivalue){
		var t_title = 'Get string';
		
		if(arguments.length == 2){
			t_title = title;
			__previous_input_ivalue = ivalue;
		}
		if(arguments.length == 1){
			t_title = title;
		}
		
		__previous_input_ivalue  = builtins.input(t_title, __previous_input_ivalue) + ''; //convert to js string
		return __previous_input_ivalue;
	}	
	
	/**
	 * terminate SL
	 */
	function exit(){
		builtins.exit();
	};	
