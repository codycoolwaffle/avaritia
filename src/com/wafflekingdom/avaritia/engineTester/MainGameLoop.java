package com.wafflekingdom.avaritia.engineTester;

import com.wafflekingdom.avaritia.entities.*;
import com.wafflekingdom.avaritia.models.*;
import com.wafflekingdom.avaritia.renderEngine.*;
import com.wafflekingdom.avaritia.shaders.*;
import com.wafflekingdom.avaritia.textures.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.*;

/**
 * Created by haslamdavid5967 on 4/27/16.
 */

public class MainGameLoop
{
	public static void main(String[] args)
	{

		DisplayManager.createDisplay(800, 800, 60);

		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		RawModel model = OBJLoader.loadOBJModel("dragon", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("sphere_infinium-ore")));

		Entity entity = new Entity(staticModel, new Vector3f(0, -5, -25), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(0, 0, -15), new Vector3f(1, 1, 1));

		Camera camera = new Camera();

		while(!Display.isCloseRequested())
		{
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
