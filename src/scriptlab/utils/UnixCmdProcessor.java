package scriptlab.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class help to parse Unix-like conventional commands to easy objects.
 * @author pika
 *
 */
public class UnixCmdProcessor {

	public String cmd=null;
	public final List<String> objects = new LinkedList<String>();
	public final Map<String, String> arguments = new HashMap<String, String>();
	
	/**
	 * Classify and parse arguments in array into their groups.
	 *  
	 * @param parts
	 * @return
	 */
	public static UnixCmdProcessor parseGroup(String[] parts){
		UnixCmdProcessor cmd = new UnixCmdProcessor();

		for(String pair : parts){
			
			try{
			//IF IS ARG?
			if(pair.startsWith("-")){
				pair = pair.substring(1).trim();
				if(pair.endsWith(",")) pair = pair.substring(0, pair.length()-1);
				
				String ss[] = pair.split("=");
				String arg = ss[0].trim();
				String val = null;
				if(ss.length>=2) {
					val = ss[1].trim();
				}
				cmd.arguments.put(arg, val);
				
					
				
			}
			
			//IF IS OBJ
			else{
//				String[] ss = pair.split(",");
//				for(String s: ss){
//					cmd.objects.add(s.trim());
//				}
				cmd.objects.addAll(seperateListObjects(pair, ","));
			}
			} catch(Exception e){
				System.err.println("#WARN: invalid syntax "+pair);
			}
			
		}
		
		return cmd;
	}
	
	/**
	 * Separate objects in list which distinguished by <code>mark</code>
	 * 
	 * @param str
	 * @param mark
	 * @return
	 */
	public static List<String> seperateListObjects(String str, String mark){
		String[] ss = str.split(mark);
		List<String> out = new LinkedList<String>();
		for(String s: ss){
			out.add(s.trim());
		}
		return out;
	}
	/**
	 * Get parsed command object as string visually.
	 * @return
	 */
	public String getStringContent(){
		String a = "objects: {\n";
		for (String string : objects) {
			a += "\t"+ string+";\n";
		}
		a+="}\n";
		a+="arguments: {\n";
		
		for (Map.Entry<String, String> entry : arguments.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    a+= "\t"+key + " : "+value+";\n";
		}
		a+="}\n";
		
		return a;
		
	}
	
}
