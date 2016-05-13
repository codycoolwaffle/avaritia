package com.wafflekingdom.avaritia.engineTester;

import com.wafflekingdom.avaritia.entities.*;
import com.wafflekingdom.avaritia.models.*;
import com.wafflekingdom.avaritia.renderEngine.*;
import com.wafflekingdom.avaritia.terrains.*;
import com.wafflekingdom.avaritia.textures.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.*;

public class MainGameLoop
{
	public static void main(String[] args)
	{

		DisplayManager.createDisplay(800, 800, 60);

		Loader loader = new Loader();

		RawModel model = OBJLoader.loadOBJModel("dragon", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("sphere_infinium-ore")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);

		Entity entity = new Entity(staticModel, new Vector3f(0, -5, -25), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(0, 0, -15), new Vector3f(1, 1, 1));

		Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("dirt_podzol_top")));
		Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("dirt_podzol_top")));

		Camera camera = new Camera();

		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested())
		{
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processEntity(entity);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
