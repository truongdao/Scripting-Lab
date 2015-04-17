clear()
var book = Xls.open('input.xls'); 
var sheet = book.sheet(0); 
var cell = sheet.cell( 0, 0); 
outln(cell.r.toString()); 






