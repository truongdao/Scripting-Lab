package scriptlab;

import java.awt.EventQueue;
import java.awt.Frame;
import java.io.FileReader;
import java.io.LineNumberReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
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
	
	/**
	 * Launch the application.<p>
	 * @param arg
	 */
	public static void main(String...arg){
		/*
		 * load engine -> run main.js -> open GUI if needed -> load GUI with main.js content 
		 */
		
		//1. load engine
		EngineLoader.initEngine(engine);
		
		//2. invoke main.js
		builtins.eval(Constants.PATH_MAIN_JS);
		
		//3. check if showing GUI is enabled - done in main.js
		if(!frame_disabled){
			Main.frame = new ScriptLabGUI();

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						//1. show frame
						Main.frame.setVisible(true);
						
						//2. load address of main.js to address bar
						txtLibPath.setText(Constants.PATH_MAIN_JS);
						
						//3. load content from main.js
						String whole ="";
						LineNumberReader nreader = new LineNumberReader(new FileReader(Constants.PATH_MAIN_JS));

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
