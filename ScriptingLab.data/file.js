var dir = {
	newf: 	null,		//new file
	newd:   null,		//new directory
	copyf:	null,		//copy a file
	
	//mode set
	path_mode: "full",	// "full" canonical path OR "relative" path OR "just_name"
						// used for ls and find
	silent: false,		// false - print out results while execute command
						// true - do not print out results
	
	ls:		null,		//full list function
	lsa:	null,		//list all
	lsf:	null,		//list all files
	lsd:	null,		//list all directories	
	
	find:	null,		//full find function
	finda: null,		// find all
	findf:	null,		//find file
	findd:	null,		//find folder
	
	//private
	check_pattern: null,	// function for check matched name;
};

///////////////////////////////////////////////////////////////////////////////

/**
 * create a file and also their path if parent dirs 
 * haven't existed
 * @return	true if OK, false if failed
 * @param {@code path} path to file
 */
dir.newf = function( path ){

	var f = new java.io.File(path);
	
	try{	
		var dir = f.getParentFile();
		if(dir!=null){
			java.nio.file.Files.createDirectories(dir.toPath()); 
			java.nio.file.Files.createFile(f.toPath());
		}	
		 return true;
	} catch(err){} 
	
	return false;
};

/**
 * create a dir and also their path if parent dirs 
 * haven't existed
 * @return	true if OK, false if failed
 * @param {@code path} path to file
 */
dir.newd = function( path ){

	var f = new java.io.File(path);
	try{	
		java.nio.file.Files.createDirectories(f.toPath()); 
		return true;
	} catch(err){outln(err.toString());} 
	return false;
};

/**
 * copy one file from {@code src} to {@code dest}
 */
dir.copyf = function (src, dest){

	java.nio.file.Files.copy(
		new java.io.File(src).toPath(),
		new java.io.File(dest).toPath()
	);
};

///////////////////////////////////////////////////////////////////////////////

/**
 * list in folder the appriciated file/directory if it is matched the pattern
 * @param	path - from where to search
 * @param	patt - pattern to find. in js OR java style
 * @param	mode - {'all'/'directory'/'file'} type of found object
 * @return	list of matched file/directory
 */
dir.ls = function( path , patt, mode){

	if(path == null) path = ".";

	var lsf = new java.io.File(path).listFiles();
	var lsn =[];
	for (var i in lsf){
		var st = (dir.path_mode == "full")? lsf[i].getCanonicalPath() : 
					(dir.path_mode == "relative")? lsf[i].getPath() : 
					lsf[i].getName(); //"just_name"
		st 	= dir.check_pattern(st, patt);
		if(st!=null){
			if(lsf[i].isDirectory()){
				if(mode=='all' || mode=='directory') lsn.push(st);
			}
			if(lsf[i].isFile()){
				if(mode=='all' || mode=='file') lsn.push(st);
			}
			
		}
	}
	
	if(!dir.silent) for(var i in lsn) outln(lsn[i]);
	
	return lsn;
};

/**
 * search for all file/directory matched the pattern in their name
 */
dir.lsa = function( path , patt){
	return dir.ls(path, patt, 'all');
};


/**
 * search for all file matched the pattern in their name
 */
dir.lsf = function( path , patt){
	return dir.ls(path, patt, 'file');
};

/**
 * search for all directory matched the pattern in their name
 */
dir.lsd = function( path , patt){
	return dir.ls(path, patt, 'directory');
};

///////////////////////////////////////////////////////////////////////////////

dir.check_pattern = function(st, patt){
	if(patt==null){	//always
		return st;
	} else{			//filter
		
		if(typeof patt == 'object' &&	//js type
			st.search(patt) >= 0){
				return st;
		}
		
		else if(typeof patt == 'string'&&	//java type
			st.matches(patt)){
				return st;
		}	
	}
	return null;	//not matched
};

///////////////////////////////////////////////////////////////////////////////

/**
 * find appriciated file/folder if it is matched the pattern
 * @param	path - from where to search
 * @param	patt - pattern to find. in js OR java style
 * @param	mode - {'all'/'directory'/'file'} type of found object
 * @return	list of matched file/folder
 */
dir.find = function(path, patt, mode){

	if(path == null) path = ".";
	
	var lsn = [];
	var root = new File(path);

	var lsFolder = function(fld){
		var lsf = fld.listFiles();
		for(var i in lsf){
			var	st = (dir.path_mode == "full")? lsf[i].getCanonicalPath() : 
					(dir.path_mode == "relative")? lsf[i].getPath() : 
					lsf[i].getName(); //"just_name"
			if(lsf[i].isDirectory()){
				var ret = dir.check_pattern(st, patt);
				if(ret!=null) {
					if(mode=='all' || mode=='directory') lsn.push(ret);
				}
				lsFolder(lsf[i]);
			}
			else{
				var ret = dir.check_pattern(st, patt);
				if(ret!=null) {
					if(mode=='all' || mode=='file') lsn.push(ret);
				}
			}
		}
	};
	lsFolder(root);
	
	if(!dir.silent) for(var i in lsn) outln(lsn[i]);
	
	return lsn;
};

/**
 * search for all file/directory matched the pattern in their name
 */
dir.finda = function(path, patt){
	return dir.find(path, patt, 'all');
};	

/**
 * search for all file matched the pattern in their name
 */
dir.findf = function(path, patt){
	return dir.find(path, patt, 'file');
};	

/**
 * search for all directory matched the pattern in their name
 */
dir.findd = function(path, patt){
	return dir.find(path, patt, 'directory');
};	

///////////////////////////////////////////////////////////////////////////////


