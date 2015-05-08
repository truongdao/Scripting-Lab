clear()
var obj_thread = new Object();
obj_thread.cnt = 0;
obj_thread.run = function(){
	try{
	while(obj_thread.cnt<3){
	  
	     sleep(1000);
		outln(''+obj_thread.cnt);
	     obj_thread.cnt++;
	}
	} catch(err){ outln('error call sleep1');}	
}
var obj2 = clone(obj_thread);
var run1 = convert('obj_thread', 'java.lang.Runnable');
var run2 = convert('obj_thread', 'java.lang.Runnable');
var thr1 = new java.lang.Thread(run1);
var thr2 = new java.lang.Thread(run2);
thr1.start();
thr2.start();

function sleep(delay){
  var start = new Date().getTime();
        while (new Date().getTime() < start + delay);
}

function clone(obj) {
    if(obj === null || typeof(obj) !== 'object')
        return obj;

    var temp = obj.constructor(); // changed

    for(var key in obj) {
        if(Object.prototype.hasOwnProperty.call(obj, key)) {
            temp[key] = clone(obj[key]);
        }
    }
    return temp;
}




