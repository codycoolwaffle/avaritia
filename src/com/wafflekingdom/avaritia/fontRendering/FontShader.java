package com.wafflekingdom.avaritia.fontRendering;

import com.wafflekingdom.avaritia.shaders.ShaderProgram;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class FontShader extends ShaderProgram
{
	
	private static final String VERTEX_FILE = "src/com/wafflekingdom/avaritia/fontRendering/fontVertex.glsl";
	private static final String FRAGMENT_FILE = "src/com/wafflekingdom/avaritia/fontRendering/fontFragment.glsl";
	
	private int location_colour;
	private int location_translation;
	
	public FontShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	protected void getAllUniformLocations()
	{
		location_colour = super.getUniformLocation("colour");
		location_translation = super.getUniformLocation("translation");
	}
	
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	protected void loadColour(Vector3f colour)
	{
		super.loadVector(location_colour, colour);
	}
	
	protected void loadTranslation(Vector2f translation)
	{
		super.load2DVector(location_translation, translation);
	}
	
}
