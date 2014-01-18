package com.h4313.deephouse.server.sensor;

import com.h4313.deephouse.exceptions.DeepHouseException;
import com.h4313.deephouse.frame.Frame;
import com.h4313.deephouse.sensor.SensorType;

public abstract class Sensor 
{
	protected String id;
	protected SensorType type;
	
	
	public String getFrame()
	{
		String frame = "";
		//TODO ecriture des trames
		frame += type;
		return frame;
	}
	
	public abstract void update(Frame frame) throws DeepHouseException;
	public abstract String getDatas();
	
}
