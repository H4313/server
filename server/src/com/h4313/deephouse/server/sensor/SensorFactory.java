package com.h4313.deephouse.server.sensor;

import com.h4313.deephouse.sensor.SensorType;

public class SensorFactory {

	public static Sensor createSensor(String id, SensorType type) {

		switch (type) {

		case LIGHT:
			return new BooleanSensor(id, type);

		case PRESENCE:
			return new BooleanSensor(id, type);

		case SWITCH:
			return new BooleanSensor(id, type);

		case SMELL:
			return new BooleanSensor(id, type);

		case SMOKE:
			return new BooleanSensor(id, type);

		case NOISE:
			return new BooleanSensor(id, type);

		case HUMIDITY:
			return new ValueSensor(id, type);

		case TEMPERATURE:
			return new ValueSensor(id, type);
		}
		return null;
	}
}
