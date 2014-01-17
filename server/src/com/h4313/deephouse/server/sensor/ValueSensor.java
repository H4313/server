package com.h4313.deephouse.server.sensor;

import com.h4313.deephouse.sensor.SensorType;

public class ValueSensor extends Sensor {

	public ValueSensor(String id, SensorType type) {
		this.id = id;
		this.type = type;
	}

	public String getDatas() {
		String datas = "";
		// TODO ecrire datas
		return datas;
	}
}
