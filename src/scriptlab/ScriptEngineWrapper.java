package scriptlab;

import java.io.Reader;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Fake ScriptEngine aiming to record all inputs to real engine.
 * These inputs (mainly code) are passed to IDE processor to 
 * catch-up changes.  
 * @author pika
 *
 */
public class ScriptEngineWrapper implements ScriptEngine, Invocable, Compilable {

	private ScriptEngine realEngine = null;
	private CodeTracker tracker = null;
	
	public ScriptEngineWrapper(String eng_name, CodeTracker tracker) {
		
		realEngine = new ScriptEngineManager(null).getEngineByName(eng_name);
		this.tracker = tracker;
	}
	@Override
	public Bindings createBindings() {
		return realEngine.createBindings();
	}

	/**
	 * all eval(...) functions are branched their parameters to IDE processor also.
	 *  
	 * @param arg0
	 * @return
	 * @throws ScriptException
	 */
	@Override
	public Object eval(String arg0) throws ScriptException {
		tracker.records.add(new CodeTracker.Record(arg0));
		return realEngine.eval(arg0);
	}

	@Override
	public Object eval(Reader arg0) throws ScriptException {
		tracker.records.add(new CodeTracker.Record(arg0));
		return realEngine.eval(arg0);
	}

	@Override
	public Object eval(String arg0, ScriptContext arg1) throws ScriptException {
		tracker.records.add(new CodeTracker.Record(arg0));
		return realEngine.eval(arg0, arg1);
	}

	@Override
	public Object eval(Reader arg0, ScriptContext arg1) throws ScriptException {
		tracker.records.add(new CodeTracker.Record(arg0));
		return realEngine.eval(arg0, arg1);
	}

	@Override
	public Object eval(String arg0, Bindings arg1) throws ScriptException {
		tracker.records.add(new CodeTracker.Record(arg0));
		return realEngine.eval(arg0, arg1);
	}

	@Override
	public Object eval(Reader arg0, Bindings arg1) throws ScriptException {
		tracker.records.add(new CodeTracker.Record(arg0));
		return realEngine.eval(arg0, arg1);
	}

	@Override
	public Object get(String arg0) {
		return realEngine.get(arg0);
	}

	@Override
	public Bindings getBindings(int arg0) {
		return realEngine.getBindings( arg0);
	}

	@Override
	public ScriptContext getContext() {
		return realEngine.getContext();
	}

	@Override
	public ScriptEngineFactory getFactory() {
		return realEngine.getFactory();
	}

	@Override
	public void put(String arg0, Object arg1) {
		//TODO: new added object should be tracked.
		 realEngine.put( arg0,  arg1) ;

	}

	@Override
	public void setBindings(Bindings arg0, int arg1) {
		realEngine.setBindings( arg0,  arg1) ;
	}

	@Override
	public void setContext(ScriptContext arg0) {
		realEngine.setContext( arg0) ;

	}
	@Override
	public <T> T getInterface(Class<T> clasz) {
		return ((Invocable)realEngine).getInterface(clasz);
	}
	@Override
	public <T> T getInterface(Object thiz, Class<T> clasz) {
		return ((Invocable)realEngine).getInterface(thiz, clasz);
	}
	@Override
	public Object invokeFunction(String name, Object... args) throws ScriptException, NoSuchMethodException {
		return ((Invocable)realEngine).invokeFunction(name, args);
	}
	@Override
	public Object invokeMethod(Object thiz, String name, Object... args) throws ScriptException, NoSuchMethodException {
		return ((Invocable)realEngine).invokeMethod(thiz, name, args);
	}
	@Override
	public CompiledScript compile(String arg0) throws ScriptException {
		return ((Compilable)realEngine).compile(arg0);
	}
	@Override
	public CompiledScript compile(Reader arg0) throws ScriptException {
		return ((Compilable)realEngine).compile(arg0);
	}

}
