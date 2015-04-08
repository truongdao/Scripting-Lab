package scriptlab;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.net.ssl.SSLEngineResult;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ComponentUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JEditorPane;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.TextArea;

import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.script.*;

import scriptlab.builtin.Console;
import scriptlab.builtin.builtins;

import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;


public class ScriptLabGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtLibPath;
	private JButton btnReset;
	private JButton btnClear;
	private JButton btnRun;
	private JButton btnLoad;
	private JButton btnBrowser;

	/**
	 * Create the frame & components.
	 */
	public ScriptLabGUI() {
		setTitle("Scripting Lab");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		UIManager.LookAndFeelInfo[] installedLafs = UIManager.
//                getInstalledLookAndFeels();
//        for (UIManager.LookAndFeelInfo lafInfo : installedLafs) {
//            try {
//                Class<?> lnfClass = Class.forName(lafInfo.getClassName());
//                LookAndFeel laf = (LookAndFeel) (lnfClass.newInstance());
//                if (laf.isSupportedLookAndFeel()) {
//                    String name = lafInfo.getName();
//                    System.out.println(name);
//                }
//            } catch (Exception ignored) {
//                // If ANYTHING weird happens, don't add this L&F
//            }
//        }
		
//    	Metal
//    	Nimbus
//    	CDE/Motif
//    	Windows
//    	Windows Classic
		
		///////////////////////////////////////////////////////////////////////
        try {
            for (LookAndFeelInfo info : UIManager.
                    getInstalledLookAndFeels()) {

                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        	ignored.printStackTrace();
        }
        
        
		///////////////////////////////////////////////////////////////////////        
		setBounds(100, 100, 619, 567);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{590, 0};
		gbl_contentPane.rowHeights = new int[]{30, 449, 23, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		///////////////////////////////////////////////////////////////////////
		btnClear = new JButton("Clear");
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{78, 70, 287, 78, 61, 0};
		gbl_panel.rowHeights = new int[]{23, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		
		///////////////////////////////////////////////////////////////////////
		btnReset = new JButton("Save");

		
		///////////////////////////////////////////////////////////////////////
		btnRun = new JButton("Run");
		
		btnRun.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_btnRun = new GridBagConstraints();
		gbc_btnRun.anchor = GridBagConstraints.NORTH;
		gbc_btnRun.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRun.insets = new Insets(0, 0, 0, 5);
		gbc_btnRun.gridx = 0;
		gbc_btnRun.gridy = 0;
		panel.add(btnRun, gbc_btnRun);
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.anchor = GridBagConstraints.NORTH;
		gbc_btnReset.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReset.insets = new Insets(0, 0, 0, 5);
		gbc_btnReset.gridx = 1;
		gbc_btnReset.gridy = 0;
		panel.add(btnReset, gbc_btnReset);
		
		txtLibPath = new JTextField();
		txtLibPath.setText("D:\\jspluginpoi.js");
		GridBagConstraints gbc_txtLibPath = new GridBagConstraints();
		gbc_txtLibPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLibPath.insets = new Insets(0, 0, 0, 5);
		gbc_txtLibPath.gridx = 2;
		gbc_txtLibPath.gridy = 0;
		panel.add(txtLibPath, gbc_txtLibPath);
		txtLibPath.setColumns(10);
		
		///////////////////////////////////////////////////////////////////////
		btnLoad = new JButton("Load");
		
		
		///////////////////////////////////////////////////////////////////////		
		btnBrowser = new JButton("Browser");
		
		GridBagConstraints gbc_btnBrowser = new GridBagConstraints();
		gbc_btnBrowser.anchor = GridBagConstraints.NORTH;
		gbc_btnBrowser.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBrowser.insets = new Insets(0, 0, 0, 5);
		gbc_btnBrowser.gridx = 3;
		gbc_btnBrowser.gridy = 0;
		panel.add(btnBrowser, gbc_btnBrowser);
		GridBagConstraints gbc_btnLoad = new GridBagConstraints();
		gbc_btnLoad.anchor = GridBagConstraints.NORTH;
		gbc_btnLoad.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoad.gridx = 4;
		gbc_btnLoad.gridy = 0;
		panel.add(btnLoad, gbc_btnLoad);
		
		///////////////////////////////////////////////////////////////////////
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(0.75);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 1;
		contentPane.add(splitPane, gbc_splitPane);
		
		
		///////////////////////////////////////////////////////////////////////
		Main.codeEditor = new JTextPane(){
			
			public boolean getScrollableTracksViewportWidth(){
				Component parent = getParent();
				ComponentUI ui = getUI();
				
				return parent !=null ? (ui.getPreferredSize(this).width <= parent.getSize().width) : true;
			}
		};
				
		Main.codeEditor.setText("out(input());");
		Main.codeEditor.setFont(new Font("Consolas", Font.BOLD, 13));
		Main.codeEditor.setForeground(Color.GREEN);
		Main.codeEditor.setCaretColor(Color.RED);
		Main.codeEditor.setBackground(Color.BLACK);
				
		JScrollPane scrl_Code = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
				);
		scrl_Code.setViewportView(Main.codeEditor);
		splitPane.setLeftComponent(scrl_Code);

				
		///////////////////////////////////////////////////////////////////////
		Main.console = new JTextPane(){
			
			public boolean getScrollableTracksViewportWidth(){
				Component parent = getParent();
				ComponentUI ui = getUI();
				
				return parent !=null ? (ui.getPreferredSize(this).width <= parent.getSize().width) : true;
			}
			
		};
		Main.console.setBackground(SystemColor.control);
		Main.console.setEditable(false);
		Main.console.setText(	Constants.GUIDE_BUILTINS		);
		
		
		///////////////////////////////////////////////////////////////////////		
		JScrollPane scrl_Result = new JScrollPane(
				
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
				);
		scrl_Result.setViewportView(Main.console);
		
		splitPane.setRightComponent(scrl_Result);
		Main.codeEditor.requestFocus();
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 2;
		contentPane.add(btnClear, gbc_btnClear);
		

		///////////////////////////////////////////////////////////////////////
		addListeners();
		
		///////////////////////////////////////////////////////////////////////
		this.setVisible(true);
	}
	
	/*
	 * add listeners to components 
	 */
	private void addListeners(){
		
		///////////////////////////////////////////////////////////////////////
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.console.setText("");
			}
		});
		
		///////////////////////////////////////////////////////////////////////
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileWriter writer;
				try {
					writer = new FileWriter(new File(Constants.PATH_MAIN_JS));
					writer.write(Main.codeEditor.getText());
					writer.flush();
					writer.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Console.print(e1.getMessage(), Console.MSGTYPE_ERROR);
				}
			}
		});
		
		///////////////////////////////////////////////////////////////////////
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					
					Main.engine.eval(Main.codeEditor.getText());

				} catch (ScriptException e) {
					e.printStackTrace();
					Console.print(e.getMessage(), Console.MSGTYPE_ERROR);
				}
			}
		});
		
		///////////////////////////////////////////////////////////////////////
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtLibPath.getText() !=null &&
						!txtLibPath.getText().isEmpty()){
					
					String lib = txtLibPath.getText().trim();
					
					String msg ;

					if(builtins.eval(lib)){
						msg = "Loaded library: "+lib+" successfully!";	
						Console.print(msg, Console.MSGTYPE_INFO);
					} else{
						msg = "Loaded library: "+lib+" failed!";	
						Console.print(msg, Console.MSGTYPE_ERROR);
					}
				}
			}
		});
		
		///////////////////////////////////////////////////////////////////////
		btnBrowser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.addChoosableFileFilter(new FileNameExtensionFilter(
						"JavaScriptingLab Plugin (.js)", "js"));
				
				int retval  = jfc.showDialog(ScriptLabGUI.this, null);
				if(retval == jfc.APPROVE_OPTION){
					String path = "";
					File file = jfc.getSelectedFile();
		            if (file != null) {
		                path = file.getPath();
		            }
					txtLibPath.setText(path);
				}
				
			}
		});
		
		///////////////////////////////////////////////////////////////////////
		
		Main.codeEditor.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				
				// Alt + Enter -> execute selected text or whole
				if(e.isAltDown() && e.getKeyCode() == KeyEvent.VK_ENTER){
					try {
						
						// eval selected text
						if(Main.codeEditor.getSelectedText()!= null &&
							!Main.codeEditor.getSelectedText().isEmpty()){
							
							Main.engine.eval(Main.codeEditor.getSelectedText());
						
						//eval whole text 
						}else{
							
							Main.engine.eval(Main.codeEditor.getText());
							
						}

					} catch (ScriptException ex) {
						
						//ex.printStackTrace();
						Console.print(ex.getMessage(), Console.MSGTYPE_ERROR);
						
					}
				}
				
				//Shift + Enter -> execute pointing line
				else if(e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_ENTER){
					try {
						
						// eval working line
						String[] lines = Main.codeEditor.getText().split("[\r|\n]");
						int ppos = Main.codeEditor.getSelectionStart();
						int start = 0;
						int end = 0;
						String wline = "";
						for(String line: lines){
							wline = line;
							end = start + line.length();
							if(ppos >= start && ppos <= end){
								break;
							}
							start = end+1;
							
						}
						
						Main.engine.eval(wline);

					} catch (Exception ex) {
						
						//ex.printStackTrace();
						Console.print(ex.getMessage(), Console.MSGTYPE_ERROR);
						
					}
				}
				else{
					super.keyReleased(e);
				}
			}
			
		});
		///////////////////////////////////////////////////////////////////////		
	}
}



