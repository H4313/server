package com.h4313.deephouse.server.sensor;

import com.h4313.deephouse.exceptions.DeepHouseException;
import com.h4313.deephouse.frame.Frame;
import com.h4313.deephouse.sensor.SensorType;

public class BooleanSensor extends Sensor {

	public BooleanSensor(String id, SensorType type) {
		this.id = id;
		this.type = type;
	}
	
	protected boolean lastValue;

	public String getDatas() {
		String datas = "";
		if(lastValue) {
			datas += "";
		}
		return datas;
	}

	@Override
	public void update(Frame frame) throws DeepHouseException {
		// TODO Auto-generated method stub
		
	}
}
