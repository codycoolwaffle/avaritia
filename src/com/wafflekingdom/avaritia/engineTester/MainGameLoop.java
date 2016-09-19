package com.wafflekingdom.avaritia.engineTester;

import com.wafflekingdom.avaritia.entities.Camera;
import com.wafflekingdom.avaritia.entities.Entity;
import com.wafflekingdom.avaritia.entities.Light;
import com.wafflekingdom.avaritia.entities.Player;
import com.wafflekingdom.avaritia.guis.GuiRenderer;
import com.wafflekingdom.avaritia.guis.GuiTexture;
import com.wafflekingdom.avaritia.models.RawModel;
import com.wafflekingdom.avaritia.models.TexturedModel;
import com.wafflekingdom.avaritia.renderEngine.DisplayManager;
import com.wafflekingdom.avaritia.renderEngine.Loader;
import com.wafflekingdom.avaritia.renderEngine.MasterRenderer;
import com.wafflekingdom.avaritia.renderEngine.OBJLoader;
import com.wafflekingdom.avaritia.terrains.Terrain;
import com.wafflekingdom.avaritia.textures.ModelTexture;
import com.wafflekingdom.avaritia.textures.TerrainTexture;
import com.wafflekingdom.avaritia.textures.TerrainTexturePack;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop
{
	private static Terrain[] terrainList;
	
	public static void main(String[] args)
	{
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		// *********TERRAIN TEXTURE STUFF***********
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		// *****************************************
		
		RawModel model = com.wafflekingdom.avaritia.renderEngine.OBJLoader.loadOBJModel("tree", loader);
		
		TexturedModel tree = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadOBJModel("grassModel", loader), new ModelTexture(loader.loadTexture("diffuse")));
		TexturedModel fern = new TexturedModel(OBJLoader.loadOBJModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
		TexturedModel bobble = new TexturedModel(OBJLoader.loadOBJModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));
		TexturedModel pine = new TexturedModel(OBJLoader.loadOBJModel("pine", loader), new ModelTexture(loader.loadTexture("pine")));
		TexturedModel lamp = new TexturedModel(OBJLoader.loadOBJModel("lamp", loader), new ModelTexture(loader.loadTexture("lamp")));
		
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		grass.getTexture().setNumberOfRows(4);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setNumberOfRows(2);
		lamp.getTexture().setUseFakeLighting(true);
		
		
		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");
		Terrain terrain2 = new Terrain(-1, 0, loader, texturePack, blendMap, "heightmap");
		Terrain terrain3 = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		Terrain terrain4 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");
		
		terrainList = new Terrain[]{terrain, terrain2, terrain3, terrain4};
		
		List<Entity> entities = new ArrayList<>();
		Random random = new Random();
		for(int i = 0; i < 5000; i++)
		{
			if(i % 3 == 0)
			{
				float x = random.nextFloat() * 1600 - 800;
				float z = random.nextFloat() * 1600 - 800;
				float y = calculateYPosOnTerrain(x, z);
				entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));
				x = random.nextFloat() * 1600 - 800;
				z = random.nextFloat() * 1600 - 800;
				y = calculateYPosOnTerrain(x, z);
				entities.add(new Entity(grass, random.nextInt(9), new Vector3f(x, y, z), 0, 0, 0, 1.8f));
				//x = random.nextFloat() * 1600 - 800;
				//z = random.nextFloat() * 1600 -800;
				//y = calculateYPosOnTerrain(x, z);
				//entities.add(new Entity(grass, random.nextInt(9), new Vector3f(x, y, z), 0, 0, 0, 2.3f));
			}
			if(i % 4 == 0)
			{
				float x = random.nextFloat() * 1600 - 800;
				float z = random.nextFloat() * 1600 - 800;
				float y = calculateYPosOnTerrain(x, z);
				//entities.add(new Entity(bobble, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.1f + 0.6f));
				//x = random.nextFloat() * 1600 - 800;
				//z = random.nextFloat() * 1600 - 800;
				//y = calculateYPosOnTerrain(x, z);
				//entities.add(new Entity(tree, new Vector3f(x, y, z), 0, 0, 0, random.nextFloat() * 1 + 4));
				//x = random.nextFloat() * 1600 - 800;
				//z = random.nextFloat() * 1600 - 800;
				//y = calculateYPosOnTerrain(x, z);
				entities.add(new Entity(pine, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.1f + 0.6f));
			}
			
		}
		
		
		List<Light> lights = new ArrayList<>();
		lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));
		lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));
		
		entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));
		
		MasterRenderer renderer = new MasterRenderer(loader);
		
		RawModel playerRaw = OBJLoader.loadOBJModel("person", loader);
		TexturedModel playerModel = new TexturedModel(playerRaw, new ModelTexture(loader.loadTexture("playerTexture")));
		
		//Player player = new Player(playerModel, new Vector3f(random.nextFloat() * 600 - 300, 5, random.nextFloat() * 600 - 300), 0, 180, 0, 0.6f);
		Player player = new Player(playerModel, new Vector3f(300, 5, -300), 0, 180, 0, 0.6f);
		Camera camera = new Camera(player);
		
		List<GuiTexture> guis = new ArrayList<>();
		GuiTexture health = new GuiTexture(loader.loadTexture("health"), new Vector2f(0.4f, -0.6f), new Vector2f(0.5f, 0.75f));
		guis.add(health);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		while(!Display.isCloseRequested())
		{
			camera.move();
			player.move(terrainList);
			renderer.processEntity(player);
			for(Terrain terrains : terrainList)
			{
				renderer.processTerrain(terrains);
			}
			for(Entity entity : entities)
			{
				renderer.processEntity(entity);
			}
			renderer.render(lights, camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}
	
	public static float calculateYPosOnTerrain(float x, float z)
	{
		float y = 0;
		for(Terrain terrains : terrainList)
		{
			float temp = terrains.getHeightOfTerrain(x, z);
			if(temp != 0)
			{
				y = temp;
				break;
			}
		}
		return y;
	}
	
}