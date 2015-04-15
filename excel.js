importPackage(java.io);
importClass(java.lang.Class);
loadjar("D:/softs/java/poi/simplecodestuffs.com/poi-3.7-20101029.jar");
importClass(Packages.org.apache.poi.hssf.usermodel.HSSFWorkbook);

var Xls = {
	path: ".",
	// APIs
	open: null 		//open excel file then return book object
};

var Book = {
	parent: null,
	r: null,
	//APIs
	sheet: null,		//open & return  1 sheet object
	sheets: null,		//get all sheets
	cell: null,			//get a cell
	cells: null,		// get all cells
	save: null
};

var Sheet = {
	r : null,
	//APIs
	cell: null,			//get a cell
	cells: null			//get all cells to an array
};

var Cell = {
	//APIs
	r: null
};
////////////////////////// IMPLEMENTATIONS ////////////////////////////////////

Xls.open = function(file_path){
	this.path = file_path;
	var new_book = Object.create(Book);
	new_book.parent = this;
	
	var fis = new FileInputStream(file_path);
	new_book.r = new HSSFWorkbook(fis);
	fis.close();
	return new_book;
};

//---------------------------------------------------------------------------//

Book.save = function(){
	var fos = new FileOutputStream(this.parent.path);
	this.r.write(fos);
	fos.close();
};

Book.sheet = function(id){
	var new_sheet = Object.create(Sheet);
	//if id is index
	if(typeof id == 'number'){
		new_sheet.r =  this.r.getSheetAt(id);
	}
	if(typeof id == 'string'){
		new_sheet.r = this.r.getSheet(id);
	}
	
	return new_sheet;
};

Book.sheets = function(){
	var sheets = [];
	for( var i=0; i< this.r.getNumberOfSheets(); i++){
		var new_sheet = Object.create(Sheet);
		new_sheet.r =  this.r.getSheetAt(i);
		sheets[i] = new_sheet;
	}
	
	return sheets;
};

Book.cell = function(sheet_id, row, col){
	var sh = this.sheet(sheet_id);
	return sh.cell(row, col);
};

Book.cells = function(sheet_id){
	var sh = this.sheet(sheet_id);
	return sh.cells();
};

//---------------------------------------------------------------------------//

Sheet.cell = function(row, col){
	var new_cell = Object.create(Cell);
	new_cell.r = this.r.getRow(row).getCell(col);
	return new_cell;
};

Sheet.cells = function(){
	var icells = [];
	
	var rowIterator = this.r.iterator();
	var row, cellIterator;
	
	while(rowIterator.hasNext()){
	
		row = rowIterator.next();
		cellIterator = row.cellIterator();
		
		while(cellIterator.hasNext()){
			var new_cell = Object.create(Cell);
			new_cell.r = cellIterator.next();
			icells[icells.length] = new_cell;
		}
	}
	return icells;
};



