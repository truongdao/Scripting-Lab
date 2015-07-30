clear();
importPackage(javax.comm);

try{
commIDs = CommPortIdentifier.getPortIdentifier("COM3");
sp = commIDs.open("MyExp1",2000);
sp.setSerialPortParams(75, SerialPort.DATABITS_8,
			SerialPort.STOPBITS_1, SerialPort.PARITY_ODD);
out = sp.getOutputStream();
out.write(new java.lang.String("Hello java RS232").getBytes());
sp.close();
out.close();
} catch(err){
 outln(err.message);
}
outln('done!');


