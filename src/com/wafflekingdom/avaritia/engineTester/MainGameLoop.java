package com.wafflekingdom.avaritia.engineTester;

import com.wafflekingdom.avaritia.entities.*;
import com.wafflekingdom.avaritia.models.*;
import com.wafflekingdom.avaritia.renderEngine.*;
import com.wafflekingdom.avaritia.terrains.*;
import com.wafflekingdom.avaritia.textures.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.*;

import java.util.*;

public class MainGameLoop
{
	public static void main(String[] args)
	{

		DisplayManager.createDisplay(800, 800, 60);

		Loader loader = new Loader();

		RawModel model = OBJLoader.loadOBJModel("tree", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);

		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i = 0; i < 500; i++)
		{
			entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));
		}

		Light light = new Light(new Vector3f(20000, 20000, 20000), new Vector3f(1, 1, 1));

		Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("grass")));

		Camera camera = new Camera();

		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested())
		{
			camera.move();
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			for(Entity entity : entities)
			{
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
