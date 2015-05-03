
importClass(java.awt.Robot);
importClass(java.awt.event.KeyEvent);

var autokey = {
	rb:	new Robot(),
	
	// press 1 key
	pk:	function (k){	
			this.rb.keyPress(k);
			this.rb.keyRelease(k);
		},
	
	// put 1 string, key by key after dt time
	pks: function (str, dt){
			for(var i=0; i<str.length; i++){
				var k = java.lang.Character.getNumericValue(str.charAt(i))+0x37;
				this.pk(k);
				this.rb.delay(dt);
			}
		},
	
	
	// press keys and put string sequence
	// ex: pkx( VK_WINDOWS, delay_500ms
	//			"CMD",		delay_500ms
	//		);
	pkx: function(){
			for(var i=0; i< arguments.length; i++){
				var pr = arguments[i];
				
				//input key(s)
				if(i%2==0){
					if(typeof pr == 'number'){
						this.pk(pr);
					}
					
					if(typeof pr == 'string'){
						this.pks(pr,500);
					}
				}

				//delay time
				else{
				  if(typeof pr != 'number'){
					outln('parameter '+i +' : delay time, should be a number');
					return;
				  }
				  this.rb.delay(pr);
				}
			}
			}
};



