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
	private static int fps;



	public static void createDisplay(int width, int height, int maxFPS)
	{
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

		WIDTH = width;
		HEIGHT = height;
		fps = maxFPS;

		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Avaritia");
			Display.create(new PixelFormat(), attribs);

			while(!Display.isCloseRequested())
			{
				updateDisplay();
			}

			closeDisplay();
			System.exit(0);
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
		Display.sync(fps);

	}

	public static void closeDisplay()
	{

		Display.destroy();

	}

}
