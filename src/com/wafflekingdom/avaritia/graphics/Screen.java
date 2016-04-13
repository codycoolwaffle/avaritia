package com.wafflekingdom.avaritia.graphics;

import com.wafflekingdom.avaritia.graphics.Render;

import java.util.*;

/**
 * Created by CeCe on 4/12/2016.
 */
public class Screen extends Render
{
	private Render test;

	public Screen(int width, int height)
	{
		super(width, height);
		Random random = new Random();
		test = new Render(256, 256);
		for(int i = 0; i < 256*256; i++)
		{
			test.pixels[i] = random.nextInt();
		}
	}

	public void render()
	{
		draw(test, 0, 0);
	}
}
