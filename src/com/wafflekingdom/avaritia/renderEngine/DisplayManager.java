package com.wafflekingdom.avaritia.renderEngine;

import org.lwjgl.*;
import org.lwjgl.opengl.*;

/**
 * Created by haslamdavid5967 on 4/27/16.
 */

public class DisplayManager
{
	private static int WIDTH;
	private static int HEIGHT;
	private static int FPS_CAP;



	public static void createDisplay(int width, int height, int maxFPS)
	{
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

		WIDTH = width;
		HEIGHT = height;
		FPS_CAP = maxFPS;

		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Avaritia");
			Display.create(new PixelFormat(), attribs);
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);

	}

	public static void updateDisplay()
	{

		Display.update();
		Display.sync(FPS_CAP);

	}

	public static void closeDisplay()
	{

		Display.destroy();

	}

}
