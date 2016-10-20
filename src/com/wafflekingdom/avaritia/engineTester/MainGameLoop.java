package com.wafflekingdom.avaritia.engineTester;

import com.wafflekingdom.avaritia.entities.Camera;
import com.wafflekingdom.avaritia.entities.Entity;
import com.wafflekingdom.avaritia.entities.Light;
import com.wafflekingdom.avaritia.entities.Player;
import com.wafflekingdom.avaritia.guis.GuiRenderer;
import com.wafflekingdom.avaritia.guis.GuiTexture;
import com.wafflekingdom.avaritia.models.RawModel;
import com.wafflekingdom.avaritia.models.TexturedModel;
import com.wafflekingdom.avaritia.normalMappingObjConverter.NormalMappedObjLoader;
import com.wafflekingdom.avaritia.renderEngine.DisplayManager;
import com.wafflekingdom.avaritia.renderEngine.Loader;
import com.wafflekingdom.avaritia.renderEngine.MasterRenderer;
import com.wafflekingdom.avaritia.renderEngine.OBJFileLoader;
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
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		// *****************************************
		
		TexturedModel rocks = new TexturedModel(OBJFileLoader.loadOBJModel("rocks", loader), new ModelTexture(loader.loadTexture("rocks")));
		
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		
		TexturedModel fern = new TexturedModel(OBJFileLoader.loadOBJModel("fern", loader), fernTextureAtlas);
		
		TexturedModel bobble = new TexturedModel(OBJFileLoader.loadOBJModel("pine", loader), new ModelTexture(loader.loadTexture("pine")));
		bobble.getTexture().setHasTransparency(true);
		
		fern.getTexture().setHasTransparency(true);
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		
		terrainList.add(terrain);
		
		TexturedModel lamp = new TexturedModel(OBJFileLoader.loadOBJModel("lamp", loader), new ModelTexture(loader.loadTexture("lamp")));
		lamp.getTexture().setUseFakeLighting(true);
		
		List<Entity> entities = new ArrayList<>();
		List<Entity> normalMapEntities = new ArrayList<>();
		
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader), new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		
		TexturedModel crateModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("crate", loader), new ModelTexture(loader.loadTexture("crate")));
		crateModel.getTexture().setNormalMap(loader.loadTexture("crateNormal"));
		crateModel.getTexture().setShineDamper(10);
		crateModel.getTexture().setReflectivity(0.5f);
		
		TexturedModel boulderModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("boulder", loader), new ModelTexture(loader.loadTexture("boulder")));
		boulderModel.getTexture().setNormalMap(loader.loadTexture("boulderNormal"));
		boulderModel.getTexture().setShineDamper(10);
		boulderModel.getTexture().setReflectivity(0.5f);
		
		Entity entity = new Entity(barrelModel, new Vector3f(75, 10, -75), 0, 0, 0, 1f);
		Entity entity2 = new Entity(boulderModel, new Vector3f(85, 10, -75), 0, 0, 0, 1f);
		Entity entity3 = new Entity(crateModel, new Vector3f(65, 10, -75), 0, 0, 0, 0.04f);
		normalMapEntities.add(entity);
		normalMapEntities.add(entity2);
		normalMapEntities.add(entity3);
		
		Random random = new Random(5666778);
		for(int i = 0; i < 60; i++)
		{
			if(i % 3 == 0)
			{
				float x = random.nextFloat() * 150;
				float z = random.nextFloat() * -150;
				if((x > 50 && x < 100)||(z < -50 && z > -100))
				{
					
				}
				else
				{
					float y = terrain.getHeightOfTerrain(x, z);
					
					entities.add(new Entity(fern, 3, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));
				}
			}
			
			if(i % 2 == 0)
			{
				float x = random.nextFloat() * 150;
				float z = random.nextFloat() * -150;
				if((x > 50 && x < 100)||(z < -50 && z > -100))
				{
					
				}
				else
				{
					float y = terrain.getHeightOfTerrain(x, z);
					
					entities.add(new Entity(bobble, 1, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.6f + 0.8f));
				}
			}
		}
		entities.add(new Entity(rocks, new Vector3f(75, 4.6f, -75), 0, 0, 0, 75));
		
		List<Light> lights = new ArrayList<>();
		Light sun = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);
		
		MasterRenderer renderer = new MasterRenderer(loader);
		
		RawModel playerRaw = OBJFileLoader.loadOBJModel("person", loader);
		TexturedModel playerModel = new TexturedModel(playerRaw, new ModelTexture(loader.loadTexture("playerTexture")));
		
		//Player player = new Player(playerModel, new Vector3f(random.nextFloat() * 600 - 300, 5, random.nextFloat() * 600 - 300), 0, 180, 0, 0.6f);
		Player player = new Player(playerModel, new Vector3f(75, 5, -75), 0, 100, 0, 0.6f);
		entities.add(player);
		Camera camera = new Camera(player);
		
		List<GuiTexture> guiTextures = new ArrayList<>();
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrainList);
		
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<>();
		WaterTile water = new WaterTile(75, -75, 0);
		waters.add(water);
		
		while(!Display.isCloseRequested())
		{
			camera.move();
			player.move(terrainList);
			
			picker.update();
			entity.increaseRotation(0, 1, 0);
			entity2.increaseRotation(0, 1, 0);
			entity3.increaseRotation(0, 1, 0);
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrainList, lights, camera, new Vector4f(0, 1, 0, -water.getHeight() + 1));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrainList, lights, camera, new Vector4f(0, -1, 0, water.getHeight() + 0.5f));
			
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrainList, lights, camera, new Vector4f(0, -1, 0, 100000));
			waterRenderer.render(waters, camera, sun);
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