package com.wafflekingdom.avaritia.textures;

/**
 * Created by haslamdavid5967 on 4/29/16.
 */

public class ModelTexture
{

	private int textureID;

	private float shineDamper = 1;
	private float reflectivity = 0;

	public ModelTexture(int id)
	{
		textureID = id;
	}

	public int getID()
	{
		return textureID;
	}
	
}
