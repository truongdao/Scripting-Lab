clear()
eval('file.js');
//dir.path_mode = "relative";
//dir.silent = true;
var ls;
dir.finda();
//ls = dir.finda(".");
//ls = dir.lsa(".");
//dir.lsf(".", "a.*");
//dir.newf("a/b/c2");
//dir.newd("a/b/c3");
//for(i in ls) outln(ls[i]);

var content = '';
var f = dir.readf('test_file.jsx');
while((ln = f.readLine())!= null) {
 outln(ln); 
 content += ln+'\n';	
}
var wf = dir.writef('test_file.jsx');
wf.write(content);
wf.close();
