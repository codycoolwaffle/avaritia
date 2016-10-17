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
import com.wafflekingdom.avaritia.toolbox.MousePicker;
import com.wafflekingdom.avaritia.water.WaterFrameBuffers;
import com.wafflekingdom.avaritia.water.WaterRenderer;
import com.wafflekingdom.avaritia.water.WaterShader;
import com.wafflekingdom.avaritia.water.WaterTile;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop
{
	private static List<Terrain> terrainList = new ArrayList<>();
	
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
		
		
		Terrain terrain1 = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");
		Terrain terrain2 = new Terrain(-1, 0, loader, texturePack, blendMap, "heightmap");
		Terrain terrain3 = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		Terrain terrain4 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");
		
		terrainList.add(terrain1);
		terrainList.add(terrain2);
		terrainList.add(terrain3);
		terrainList.add(terrain4);
		
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
		lights.add(new Light(new Vector3f(185, calculateYPosOnTerrain(185, -293) + 15, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(370, calculateYPosOnTerrain(370, -300) + 15, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(293, calculateYPosOnTerrain(293, -305) + 15, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));
		
		entities.add(new Entity(lamp, new Vector3f(185, calculateYPosOnTerrain(185, -293), -293), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(370, calculateYPosOnTerrain(370, -300), -300), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(293, calculateYPosOnTerrain(293, -305), -305), 0, 0, 0, 1));
		
		MasterRenderer renderer = new MasterRenderer(loader);
		
		RawModel playerRaw = OBJLoader.loadOBJModel("person", loader);
		TexturedModel playerModel = new TexturedModel(playerRaw, new ModelTexture(loader.loadTexture("playerTexture")));
		
		//Player player = new Player(playerModel, new Vector3f(random.nextFloat() * 600 - 300, 5, random.nextFloat() * 600 - 300), 0, 180, 0, 0.6f);
		Player player = new Player(playerModel, new Vector3f(175, 250, -250), 0, 180, 0, 0.6f);
		entities.add(player);
		Camera camera = new Camera(player);
		
		List<GuiTexture> guiTextures = new ArrayList<>();
		GuiTexture health = new GuiTexture(loader.loadTexture("health"), new Vector2f(0.4f, -0.6f), new Vector2f(0.5f, 0.75f));
		guiTextures.add(health);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrainList);
		
		Entity lampEntity = new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1);
		
		entities.add(lampEntity);
		
		Light light = new Light(new Vector3f(293, -6.8f, -305), new Vector3f(5, 5, 5), new Vector3f(1, 0.01f, 0.002f));
		
		lights.add(light);
		
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<>();
		WaterTile water = new WaterTile(175, -250, 0);
		waters.add(water);
		
		while(!Display.isCloseRequested())
		{
			camera.move();
			player.move(terrainList);
			
			picker.update();
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, terrainList, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, terrainList, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));
			
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			renderer.renderScene(entities, terrainList, lights, camera, new Vector4f(0, -1, 0, 100000));
			waterRenderer.render(waters, camera);
			guiRenderer.render(guiTextures);
			
			DisplayManager.updateDisplay();
		}
		
		buffers.cleanUp();
		waterShader.cleanUp();
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