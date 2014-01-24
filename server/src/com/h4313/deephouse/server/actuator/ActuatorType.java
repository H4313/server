package com.h4313.deephouse.server.actuator;

public enum ActuatorType {
	RADIATOR(false,"00000000"), 
	CLIMA(false,"00000000");
	
	/*
	 * true if the actuator is a physical one false if it's a virtual one
	 */
	private final boolean physical;
	
	//typeFrame has to be 4 byte long => 8 hexa characters
	private final String typeFrame;

	ActuatorType(boolean physical, String typeFrame) {
		this.typeFrame = typeFrame;
		this.physical = physical;
	}

	public boolean isPhysical() {
		return physical;
	}
	
	public String getTypeFrame() {
		return typeFrame;
	}
}
