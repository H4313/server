package com.h4313.deephouse.server.actuator;

import com.h4313.deephouse.server.sensor.SensorSet;

public abstract class Actuator {
	protected String id;
	//todo : maybe a list (synchronized)
	protected SensorSet sensors;
	
	public Actuator(String id){
		this.id = id;
		sensors = new SensorSet();
	}
}
