package com.wafflekingdom.avaritia.models;

import com.wafflekingdom.avaritia.textures.*;

/**
 * Created by haslamdavid5967 on 4/29/16.
 */

public class TexturedModel
{

	private RawModel rawModel;
	private ModelTexture texture;

	public TexturedModel(RawModel model, ModelTexture texture)
	{
		rawModel = model;
		this.texture = texture;
	}

	public RawModel getRawModel()
	{
		return rawModel;
	}

	public ModelTexture getTexture()
	{
		return texture;
	}
}
