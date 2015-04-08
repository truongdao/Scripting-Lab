importPackage(java.io);
importClass(java.lang.Class);
loadjar("D:/softs/java/poi/simplecodestuffs.com/poi-3.7-20101029.jar");
importClass(Packages.org.apache.poi.hssf.usermodel.HSSFWorkbook);

var xls = {
	// variables
	cur_book: null,
	cur_path: ".",
	// APIs
	open: null,
	save: null
};

xls.open = function(file_path){
	outln("this is openxls");
	var fis = new FileInputStream(file_path);
	var x = new HSSFWorkbook(fis);
	outln(x.getSheetName(0));
	
};