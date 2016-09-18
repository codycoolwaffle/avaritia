package com.wafflekingdom.avaritia.models;

import com.wafflekingdom.avaritia.textures.ModelTexture;

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
