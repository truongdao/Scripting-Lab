clear()
eval('ScriptingLab.data/room.js')
outln(room.data.num1)
room.data['class']= 'preIELT03';
room.data['more'] = 
	{
	name: 'truong',
	age: 23
	};
room.flush();

eval('ScriptingLab.data/room.js')
outln(room.data['more']['name'])


