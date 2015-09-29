package scriptlab;

import java.io.Reader;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Track executed code pies, content should be readed out by ide.
 * @author pika
 *
 */
public class CodeTracker {
	
	public ConcurrentLinkedQueue<Record> records = new ConcurrentLinkedQueue<Record>();
	
	
	public static class Record{
		public String codeString = null;
		public Reader codeReader = null;
		
		public Record(String codeString){
			this.codeString = codeString;
		}
		
		public Record(Reader codeReader){
			this.codeReader = codeReader;
		}
	};

}
