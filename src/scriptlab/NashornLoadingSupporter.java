package scriptlab;

import javax.script.*;
import jdk.nashorn.api.scripting.*;

/**
 * This class is create to support loading Nashorn engine if ran with java 8.
 * Which initializes particular feature supported in Nashorn (heredoc...)
 * The reason for the class is that: this program is written to support Rhino/Nashorn
 * unfortunately their architectures differ and their system libraries NOT SUPPORT cross-other
 * 
 * @author pika
 *
 */
public class NashornLoadingSupporter {

	/**
	 * all options document:
	 * http://cr.openjdk.java.net/~sundar/8020015/webrev.01/src/jdk/nashorn/internal/runtime/resources/Options.properties.html
	 * ref: http://stackoverflow.com/questions/23660416/how-to-set-java-8-nashorn-javascript-engine-options-for-a-single-engine-instance
	 * @param option
	 * @return
	 */
	 @SuppressWarnings("restriction")
	public ScriptEngine getNashornScriptEngine(String[] options){
		NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = 
            factory.getScriptEngine(options);
        
        return engine;
	}
	
}
