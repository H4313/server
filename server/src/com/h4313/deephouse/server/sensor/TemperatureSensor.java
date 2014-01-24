package com.h4313.deephouse.server.sensor;

import com.h4313.deephouse.exceptions.DeepHouseException;
import com.h4313.deephouse.frame.Frame;
import com.h4313.deephouse.sensor.SensorType;

public class TemperatureSensor extends Sensor {

	public TemperatureSensor(String id, SensorType type) {
		this.id = id;
		this.type = type;
	}

	public String getDatas() {
		String datas = "";
		// TODO ecrire datas
		return datas;
	}

	@Override
	public void update(Frame frame) throws DeepHouseException {
		// TODO Auto-generated method stub
		
	}
}
