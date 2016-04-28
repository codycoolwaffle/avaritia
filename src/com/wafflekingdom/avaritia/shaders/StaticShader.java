package com.wafflekingdom.avaritia.shaders;

/**
 * Created by haslamdavid5967 on 4/28/16.
 */

public class StaticShader extends ShaderProgram
{

	private static final String VERTEX_FILE = "src/com/wafflekingdom/avaritia/shaders/vertexShader";
	private static final String FRAGMENT_FILE = "src/com/wafflekingdom/avaritia/shaders/fragmentShader";
	
	public StaticShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}
}
