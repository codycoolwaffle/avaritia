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
		
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		grass.getTexture().setNumberOfRows(4);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setNumberOfRows(2);
		bobble.getTexture().setNumberOfRows(2);
		
		
		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");
		Terrain terrain2 = new Terrain(-1, 0, loader, texturePack, blendMap, "heightmap");
		Terrain terrain3 = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		Terrain terrain4 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");
		
		terrainList = new Terrain[] {terrain, terrain2, terrain3, terrain4};
		
		List<Entity> entities = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 5000; i++) {
			if (i % 7 == 0) {
				float x = random.nextFloat() * 1600 - 800;
				float z = random.nextFloat() * 1600 -800;
				float y = calculateYPosOnTerrain(x, z);
				entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));
				x = random.nextFloat() * 1600 - 800;
				z = random.nextFloat() * 1600 -800;
				y = calculateYPosOnTerrain(x, z);
				entities.add(new Entity(grass, random.nextInt(9), new Vector3f(x, y, z), 0, 0, 0, 1.8f));
				//x = random.nextFloat() * 1600 - 800;
				//z = random.nextFloat() * 1600 -800;
				//y = calculateYPosOnTerrain(x, z);
				//entities.add(new Entity(grass, random.nextInt(9), new Vector3f(x, y, z), 0, 0, 0, 2.3f));
			}
			if (i % 3 == 0) {
				float x = random.nextFloat() * 1600 - 800;
				float z = random.nextFloat() * 1600 -800;
				float y = calculateYPosOnTerrain(x, z);
				entities.add(new Entity(bobble, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.1f + 0.6f));
				x = random.nextFloat() * 1600 - 800;
				z = random.nextFloat() * 1600 -800;
				y = calculateYPosOnTerrain(x, z);
				entities.add(new Entity(tree, new Vector3f(x, y, z), 0, 0, 0, random.nextFloat() * 1 + 4));
				x = random.nextFloat() * 1600 - 800;
				z = random.nextFloat() * 1600 -800;
				y = calculateYPosOnTerrain(x, z);
				entities.add(new Entity(pine, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.1f + 0.6f));
			}
			
		}
		
		
		Light light = new Light(new Vector3f(20000, 20000, 20000), new Vector3f(1, 1, 1));
		
		MasterRenderer renderer = new MasterRenderer();
		
		RawModel playerRaw = OBJLoader.loadOBJModel("person", loader);
		TexturedModel playerModel = new TexturedModel(playerRaw, new ModelTexture(loader.loadTexture("playerTexture")));
		
		Player player = new Player(playerModel, new Vector3f(100, 5, -150), 0, 180, 0, 0.6f);
		Camera camera = new Camera(player);
		
		List<GuiTexture> guis = new ArrayList<>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		GuiTexture gui2 = new GuiTexture(loader.loadTexture("thinmatrix"), new Vector2f(0.30f, 0.58f), new Vector2f(0.4f, 0.4f));
		guis.add(gui2);
		guis.add(gui);
		
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
			renderer.render(light, camera);
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