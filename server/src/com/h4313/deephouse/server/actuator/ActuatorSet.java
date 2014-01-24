package com.h4313.deephouse.server.actuator;

import java.util.Hashtable;

import com.h4313.deephouse.exceptions.DeepHouseDuplicateException;
import com.h4313.deephouse.exceptions.DeepHouseException;

public class ActuatorSet extends Hashtable<String, Actuator> {
	
	public ActuatorSet() {
		super();
	}

	public void addActuator(String id, ActuatorType type) throws DeepHouseException {
		if (this.get(id) != null) {
			throw new DeepHouseDuplicateException("Id " + id + "already taken");
		}
		this.put(id,  ActuatorFactory.createActuator(id, type));
	}
}
