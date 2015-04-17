package scriptlab;

import java.awt.EventQueue;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import scriptlab.builtin.builtins;

public class Main {

	/////////////////////// COMMON DATA //////////////////////////////////
	
	public static ScriptEngine engine = 
					new ScriptEngineManager().getEngineByName("JavaScript");
	public static Frame frame;
	public static boolean frame_disabled = false;
	public static JTextPane console;
	public static JEditorPane codeEditor;
	public static  JTextField txtLibPath;	
	
	 /////////////////////// COMMON DATA //////////////////////////////////
	static String executing_file = null;
	
	/**
	 * Launch the application.<p>
	 * @param arg
	 */
	public static void main(String...arg){
		/*
		 * load engine -> run main.js/pointed jsx -> open GUI if needed -> load GUI with main.js or pointed jsx content 
		 */
		
		//1. load engine
		EngineLoader.initEngine(engine);
		
		//2. invoke config.js
		try {
			engine.eval(new FileReader(new File(Constants.PATH_CONFIG_JS)));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		//3. try to exec input file if it presents
		try {
			if(arg.length>0) {
				
				File f = new File(arg[0]);
				executing_file = f.getAbsolutePath();
				engine.eval(new FileReader(f));
				
			}
		//4. execute main.js in folder if it presents
			else {
				executing_file = new File(Constants.PATH_MAIN_JS).getAbsolutePath();
				builtins.eval(executing_file);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		
		//5. check if showing GUI is enabled - done in main.js
		if(!frame_disabled){
			Main.frame = new ScriptLabGUI();

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						//1. show frame
						Main.frame.setVisible(true);
						
						//2. load address of main.js to address bar
						txtLibPath.setText(executing_file);
						
						//3. load content from main.js
						String whole ="";
						LineNumberReader nreader = new LineNumberReader(new FileReader(executing_file));

						String line;
						while((line=nreader.readLine())!=null){
							whole += line+"\n";
						}
						Main.codeEditor.setText(whole +"\n");
			
						nreader.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		
	}
}
