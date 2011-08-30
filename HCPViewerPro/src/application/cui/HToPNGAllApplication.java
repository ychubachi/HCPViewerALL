/*
 * CommandToSVG.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.cui;

/**
 * Class CommandToSVG
 * 
 * @author macchan
 * @version $Id: CommandToSVG.java,v 1.5 2009/09/10 03:48:32 macchan Exp $
 */
public class HToPNGAllApplication extends HAbstractAllCUIApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		HToPNGAllApplication command = new HToPNGAllApplication();
		command.run(args);
	}

	public void doProcess(String in, String outdir) {
		HToPNGApplication.main(new String[] { in, outdir });
	}

}
