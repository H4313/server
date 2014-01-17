package com.h4313.deephouse.server.sensor;

public abstract class Sensor 
{
	protected String id;
	
	protected String type;
	
	
	public String getFrame()
	{
		String frame = "";
		//TODO ecriture des trames
		frame += type;
		return frame;
	}
	
	public abstract String getDatas();
	
}
