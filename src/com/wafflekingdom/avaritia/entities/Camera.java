package com.wafflekingdom.avaritia.entities;

import org.lwjgl.input.*;
import org.lwjgl.util.vector.*;

/**
 * Created by haslamdavid5967 on 4/29/16.
 */

public class Camera
{

	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;

	public Camera()
	{

	}

	public void move()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			position.y += 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			position.y -= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			position.x += 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			position.x -= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.z += 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.z -= 0.02f;
		}
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public float getPitch()
	{
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public float getRoll()
	{
		return roll;
	}
}
