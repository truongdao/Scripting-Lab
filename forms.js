
function Form(ls_items){
	this.rows = ls_items;
	this.form_dialog = null;
	this.ret = null;
	
	// append new row to the last
	this.append = function(item){
		this.rows[this.rows.length] = item;
	};
	
	//insert new row to last-1, push the last go 1 step down
	this.append_1 = function(item){
		var last = this.rows[this.rows.length-1];
		this.rows[this.rows.length-1] = item;
		this.rows[this.rows.length] = last;
	};
	
	//
	this.show = function(){
		//1. construct frame
		importPackage(javax.swing);
		form_dialog = new javax.swing.JDialog(new JFrame(), true);
		var panel = new JPanel();
		var scr_panel = new JScrollPane(panel);
		var gbl_panel = new java.awt.GridBagLayout();
		panel.setLayout(gbl_panel);
		form_dialog.add(scr_panel);
		
		//2. built form
		var temp_name = this.rows;
		for(var i=0; i<temp_name.length; i++) {											
			var j = 0;																	
			for( x in temp_name[i]){													
				var	gbc_btnRun = new java.awt.GridBagConstraints(); 					
				gbc_btnRun.anchor = gbc_btnRun.WEST;									
				gbc_btnRun.gridx = 	j;													
				gbc_btnRun.gridy = 	i; 	
				temp_name[i][x].up = this;				
				panel.add(temp_name[i][x].real_comp, gbc_btnRun);						
				j++;																	
			}																			
		}																				
		//3. show it up
		form_dialog.setVisible(true);
		
		//4. wait for having done then get return
		
	};
	
	//
	this.hide = function(){
		form_dialog.setVisible(false);
	};
}

///////////////////////////////////////////////////////////////////////////////
//		BUILT-IN COMPONENTS		//
///////////////////////////////////////////////////////////////////////////////
/**
 * interface of Components
 */
function Component(init){
	//the form containing this component
	this.up = null;
	
	//real java.awt.Component
	this.real_comp= null;	
	
	//abstract function to get value
	this.value= function(){};
}


function OKButton(value){													
	var th = {			
	'up': null,
	'real_comp': new javax.swing.JButton(value),						
	'value': function(){											
		return th.real_comp.getText();									
	}														
	};																	

	return th;															
}

function Label(value){													
	var th = {	
	'up': null,	
	'real_comp': new javax.swing.JLabel(value),						
	'value': function(){											
		return th.real_comp.getText();									
	}														
	};																	
	return th;															
}

function TextField(value){													
	var th = {			
	'up': null,	
	'real_comp': new javax.swing.JTextField(value, 15),						
	'value': function(){											
		return th.real_comp.getText();									
	}														
	};																	
	return th;															
}	
	