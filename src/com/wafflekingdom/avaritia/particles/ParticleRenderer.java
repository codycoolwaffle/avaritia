package com.wafflekingdom.avaritia.particles;

import com.wafflekingdom.avaritia.entities.Camera;
import com.wafflekingdom.avaritia.models.RawModel;
import com.wafflekingdom.avaritia.renderEngine.Loader;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

public class ParticleRenderer
{
	
	private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};
	
	private RawModel quad;
	private ParticleShader shader;
	
	protected ParticleRenderer(Loader loader, Matrix4f projectionMatrix)
	{
		
	}
	
	protected void render(List<Particle> particles, Camera camera)
	{
		
	}
	
	//The code below is for the updateModelViewMatrix() method
	//modelMatrix.m00 = viewMatrix.m00;
	//modelMatrix.m01 = viewMatrix.m10;
	//modelMatrix.m02 = viewMatrix.m20;
	//modelMatrix.m10 = viewMatrix.m01;
	//modelMatrix.m11 = viewMatrix.m11;
	//modelMatrix.m12 = viewMatrix.m21;
	//modelMatrix.m20 = viewMatrix.m02;
	//modelMatrix.m21 = viewMatrix.m12;
	//modelMatrix.m22 = viewMatrix.m22;
	
	protected void cleanUp()
	{
		shader.cleanUp();
	}
	
	private void prepare()
	{
		
	}
	
	private void finishRendering()
	{
		
	}
	
}
