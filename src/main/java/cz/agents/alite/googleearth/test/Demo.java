package cz.agents.alite.googleearth.test;

import cz.agents.alite.googleearth.handler.Synthetiser;


/**
 * 
 * @author Ondrej Vanek
 */
public class Demo {


	public static void main(String[] args) {
		Synthetiser synthetizer = new Synthetiser();
		try
		{
		    synthetizer.init();
		    synthetizer.addHandler(new DemoHandler(), DemoHandler.LINK);
		} catch (Exception e)
		{
		    e.printStackTrace();
		}
	}
}
