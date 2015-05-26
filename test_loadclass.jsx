var fr = loadclass('./SampleFrame.class');
fr.textField.setText('Your name');

var jstxtAction = new Object();
jstxtAction.actionPerformed = function(ev){
	outln(fr.textField.getText());
};
var jtxtAction = convert('jstxtAction', 'java.awt.event.ActionListener');
fr.textField.addActionListener(jtxtAction);

fr.show()



