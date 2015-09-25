package scriptlab.plugin;

import javax.swing.JFrame;
import scriptlab.Spider;

public abstract class SpiderIde extends JFrame{

	protected Spider spider;
	public SpiderIde(Spider spider){
		this.spider = spider;
	}
	
	public abstract void init();
}
