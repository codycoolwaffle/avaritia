package com.wafflekingdom.avaritia.particles;


import com.wafflekingdom.avaritia.entities.Player;
import com.wafflekingdom.avaritia.renderEngine.DisplayManager;

import org.lwjgl.util.vector.Vector3f;

public class Particle
{
	
	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;
	
	private float elapsedTime = 0;
	
	public Particle(Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength,
	                float rotation, float scale)
	{
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleMaster.addParticle(this);
	}
	
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
	
	public void setVelocity(Vector3f velocity)
	{
		this.velocity = velocity;
	}
	
	public void setGravityEffect(float gravityEffect)
	{
		this.gravityEffect = gravityEffect;
	}
	
	public void setLifeLength(float lifeLength)
	{
		this.lifeLength = lifeLength;
	}
	
	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}
	
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	
	public float getRotation()
	{
		return rotation;
	}
	
	public float getScale()
	{
		return scale;
	}
	
	protected boolean update()
	{
		velocity.y += Player.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		Vector3f.add(change, position, position);
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}
}
