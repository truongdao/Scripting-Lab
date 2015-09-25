
/*****************************************************************************/
/*    S P I D E R   I N F O   T R E E                                        */
/*****************************************************************************/

var spider = {
		x : __SPIDER_GLOBAL__
};

/**
 * add jar library to JVM class path, from then packages can be imported .<p>
 * 
 * @param jar_url - path to jar file
 * @return - {@code true} if load OK, {@code false} if fail
 */
function loadjar(jar_url){
	return spider.x.builtins.loadjar(jar_url);
}

/**
 * load js file as library.<p>
 * 
 * @param jar_url - path to jar file
 * @return - {@code true} if load OK, {@code false} if fail
 */
function include(js_url){
	spider.x.builtins.include(js_url);
}


/**
 * print string to console or SL or JVM
 * @param msg - Message to be shown on console
 */
function out(msg){
	spider.x.builtins.out(msg);
}

/**
 * print string and new line to console or SL or JVM
 * @param msg - Message to be shown on console
 */
function outln(msg){
	spider.x.builtins.out(msg + "\n");
}

/**
 * clear SL console
 */
function clear (){

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
	
	__previous_input_ivalue  = spider.x.builtins.input(t_title, __previous_input_ivalue) + ''; //convert to js string
	return __previous_input_ivalue;
}	

/**
 * terminate SL
 */
function exit(){
	spider.x.builtins.exit();
};	