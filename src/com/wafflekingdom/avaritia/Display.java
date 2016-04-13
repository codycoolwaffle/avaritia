package com.wafflekingdom.avaritia;

import com.wafflekingdom.avaritia.graphics.Render;
import com.wafflekingdom.avaritia.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Created by CeCe on 4/12/2016.
 */

public class Display extends Canvas implements Runnable
{
	public static final long serialVersionUID = 1L;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 450;
	public static final String TITLE = "Avaritia";

	private Thread thread;
	private Screen screen;
	private BufferedImage img;
	private Render render;
	private boolean running = false;
	private int[] pixels;

	public Display()
	{
		screen = new Screen(WIDTH, HEIGHT);
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
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
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}

		screen.render();

		for(int i = 0; i <WIDTH*HEIGHT; i++)
		{
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
		bs.show();
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
