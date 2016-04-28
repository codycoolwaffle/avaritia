package com.wafflekingdom.avaritia.engineTester;

import com.wafflekingdom.avaritia.renderEngine.*;
import com.wafflekingdom.avaritia.shaders.*;
import org.lwjgl.opengl.*;

/**
 * Created by haslamdavid5967 on 4/27/16.
 */

public class MainGameLoop
{
	public static void main(String[] args)
	{

		DisplayManager.createDisplay(800, 600, 60);

		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();

		float[] vertices = {-0.5f, 0.5f, 0f,  //V0
		                    -0.5f, -0.5f, 0f, //V1
		                    0.5f, -0.5f, 0f,  //V3
		                    0.5f, 0.5f, 0f    //V4
		};

		int[] indices = {0, 1, 3, //Top Left Triangle (V0, V1, V3)
		                 3, 1, 2  //Bottom Right Triangle (V3, V1, V2)
		};

		RawModel model = loader.loadToVAO(vertices, indices);

		while(!Display.isCloseRequested())
		{
			renderer.prepare();
			//Game logic
			shader.start();
			renderer.render(model);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
