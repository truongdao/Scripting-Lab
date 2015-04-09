package scriptlab.builtin;

import scriptlab.Main;



public class Console {

	
	public final static int	MSGTYPE_ERROR = 0;
	public final static int	MSGTYPE_WARNING = 1;
	public final static int	MSGTYPE_INFO = 2;

	
	public static void print(String str, int type){
		//TODO - handle MsgType
		//CASE1 : print to JVM
		if(Main.console == null){
			System.out.print(str);
		}
		
		//CASE2: print to JS Engine
		else {
			Main.console.setText(Main.console.getText()+str);
		}
	}
	
	public static void clear(){
		//TODO- handle MsgType
		//CASE1 : print to JVM
		if(Main.console == null){
		}
		
		//CASE2: print to JS Engine
		else {
			Main.console.setText("");
		}
	}
	
	/**
	 * return name of jar at runtime.<p>
	 * from: http://asolntsev.blogspot.com/2008/03/how-to-find-which-jar-file-contains.html
	 * @param clazz
	 * @return
	 */
	public static String getCodeSource(Class clazz) {
		if (clazz == null
				|| clazz.getProtectionDomain() == null
				|| clazz.getProtectionDomain().getCodeSource() == null
				|| clazz.getProtectionDomain().getCodeSource().getLocation() == null)

			// This typically happens for system classloader
			// (java.lang.* etc. classes)
			return null;

		 // Pattern for Jar name "(.*/)(.*)([.]jar$) 
		return clazz.getProtectionDomain().getCodeSource().getLocation()
				.toString();
	}
}
