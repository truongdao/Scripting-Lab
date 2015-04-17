importPackage(java.io);
loadjar("ScriptingLab.data/poi-3.7-20101029.jar")
importClass(Packages.org.apache.poi.hssf.usermodel.HSSFWorkbook)

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
	save: null,			//save to current file
	saveAs:	null		//save to new file
};

var Sheet = {
	r : null,
	book: null,
	//APIs
	left:	null,
	right:	null,
	cell: null,			//get a cell
	cells: null			//get all cells to an array
};


var Cell = {
	r:		null,
	sheet:	null,
	//APIs
	up:		null,
	down:	null,
	left:	null,
	right:	null,
	empty:	null
	
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

Book.saveAs = function(path){
	var fos = new FileOutputStream(path);
	this.r.write(fos);
	fos.close();
};

Book.sheet = function(id){
	var new_sheet = Object.create(Sheet);
	new_sheet.book = this;
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
		new_sheet.book = this;
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
	//mapping its parent
	new_cell.sheet = this;
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
			//mapping its parent
			new_cell.sheet = this;
			new_cell.r = cellIterator.next();
			icells[icells.length] = new_cell;
		}
	}
	return icells;
};


/**
 * Get a right Sheet from the current sheet an {@code offset} number of sheets.
 *
 * @param {@code offset} space in sheet unit.
 * @return right sheet or {@code null}
 */
Sheet.right = function ( offset ) {
	var right = Object.create(Sheet);
	right.book = this.book;
	try{
		right.r = this.book.r.getSheetAt(
					this.book.r["getSheetIndex(java.lang.String)"](this.r.getSheetName())
				+	offset	);
		return right;		
	}catch(err){ return null; }
};

/**
 * Get a left Sheet from the current sheet an {@code offset} number of sheets.
 *
 * @param {@code offset} space in sheet unit.
 * @return left sheet or {@code null}
 */
Sheet.left = function ( offset ) {
	return this.right( -offset);
}
//---------------------------------------------------------------------------//
/**
 * Get a Cell below the current Cell an {@code offset} number of rows.
 *
 * @param {@code offset} space in row unit.
 * @return below cell or {@code null}
 */
Cell.down = function ( offset ){
	var below = Object.create(Cell);
	below.sheet = this.sheet;

	try{
		var below_row = this.sheet.r.getRow(this.r.getRowIndex() +  offset);
		if(below_row !=null)
			below.r = below_row.getCell(this.r.getColumnIndex());
		else below.r = null;
		
	} catch(err){ below.r = null;}
	return below;
};

/**
 * Get a Cell on the right side the current Cell an {@code offset} number of rows.
 *
 * @param {@code offset} space in row unit. (>=0)
 * @return right cell or {@code null}
 */
Cell.right = function ( offset ){
	var cur_row = this.r.getRow();
	var	icur_col = this.r.getColumnIndex();
	var left_cell = Object.create(Cell);
	left_cell.sheet = this.sheet;
	try{
		left_cell.r = cur_row.getCell(icur_col + offset);
	} catch(err){ left_cell.r = null;}
	return left_cell;
};


/**
 * Get a Cell above the current Cell an {@code offset} number of rows.
 *
 * @param {@code offset} space in row unit.
 * @return above cell or {@code null}
 */
Cell.up = function ( offset ){
	return this.down(-offset);
};

/**
 * Get a Cell on the left side the current Cell an {@code offset} number of rows.
 *
 * @param {@code offset} space in row unit. (>=0)
 * @return left cell or {@code null}
 */
Cell.left = function ( offset ){
	return this.right(-offset);
};


/**
 * check if a Cell is not initialised or empty
 */
Cell.empty = function(){
	if(this.r == null || this.r.toString().isEmpty())
		return true;
	else
		return false;
}
