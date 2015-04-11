clear()
eval('forms.js')

var form1 = new Form([
	{'0': Label('Name:'),  name: TextField('Truong') },
	{'1': Label('Age:'),	  age: TextField(''), '':Label('*required')},
	{'comp3': Label('Age:'), 'comp4': TextField('') },
	]);

form1.append(  {'abc': OKButton('OK')}   )

	//
	var action = new Object();
	action.actionPerformed = function( arg0 ) {
				outln('click me!');
		form1.rows[last_row].abc.up.hide();	
		form1.rows[last_row].abc.up.ret = 10;
	}
	var act = convert('action', 'java.awt.event.ActionListener');
	var last_row = form1.rows.length -1;
	form1.rows[last_row].abc.real_comp.addActionListener(act);	

form1.show();

if(form1.ret == 10){
	outln(form1.rows[0]['name'].value())
	outln('Form submitted!')
} else {
	outln('Form broken!')
}







