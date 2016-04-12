package com.wafflekingdom.avaritia.graphics;

/**
 * Created by CeCe on 4/12/2016.
 */
public class Render
{
	public final int width;
	public final int height;
	public final int[] pixels;

	public Render(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
	}
}
