package com.wafflekingdom.avaritia;

import com.wafflekingdom.avaritia.graphics.*;

import javax.swing.*;
import java.awt.Canvas;

/**
 * Created by CeCe on 4/12/2016.
 */

public class Display extends Canvas implements Runnable
{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 450;
	public static final String TITLE = "Avaritia";

	private Thread thread;
	private boolean running = false;
	private Render render;

	public Display()
	{
		render = new Render(WIDTH, HEIGHT);
	}

	private void start()
	{
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private void stop()
	{
		if(!running)
			return;
		running = false;
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void run()
	{
		while(running)
		{
			tick();
			render();
		}
	}

	private void tick()
	{

	}

	private void render()
	{

	}

	public static void main(String[] args)
	{
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		game.start();
	}
}
