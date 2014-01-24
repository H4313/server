package com.h4313.deephouse.server.actuator;

import com.h4313.deephouse.server.sensor.SensorSet;
import com.h4313.deephouse.server.util.Constant;

public abstract class Actuator {
	protected String id;
	protected ActuatorType type;
	//todo : maybe a list (synchronized)
	protected SensorSet sensors;
	
	public Actuator(String id){
		this.id = id;
		sensors = new SensorSet();
	}
	
	public String getFrame() {
		String frame = "";
		// TODO ecriture des trames
		frame += type.getTypeFrame();
		frame += getDatas();
		frame += id;
		frame += Constant.FRAME_STATUS_AND_CHECKSUM;
		return frame;
	}
	
	public abstract String getDatas();
}
