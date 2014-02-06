package com.h4313.deephouse.server.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.h4313.deephouse.actuator.Actuator;
import com.h4313.deephouse.actuator.ActuatorSet;
import com.h4313.deephouse.actuator.ActuatorType;
import com.h4313.deephouse.exceptions.DeepHouseException;
import com.h4313.deephouse.frame.Frame;
import com.h4313.deephouse.housemodel.House;
import com.h4313.deephouse.housemodel.Room;
import com.h4313.deephouse.sensor.Sensor;
import com.h4313.deephouse.sensor.SensorSet;
import com.h4313.deephouse.sensor.SensorType;

public class Controller extends Thread {
	
	private volatile boolean alive;
	
	private static volatile Controller instance = null;

	protected House house;
	
	protected SensorsListener sensorsListener;
	
	protected ActuatorsSender actuatorsSender;
	
	/**
	 * Constructeur du controleur
	 */
	private Controller() {
		super();
        this.alive = true;
	}
	
	public static final Controller getInstance() {
        if (Controller.instance == null) {
            synchronized(Controller.class) {
              if (Controller.instance == null) {
             	 Controller.instance = new Controller();
              }
            }
         }
         return Controller.instance;
	}
	
	
    public void initActuatorsSender(String host, int port) {
    	actuatorsSender = new ActuatorsSender(host,port);
    }
    
    public void initSensorsListener(int port) {
    	sensorsListener = new SensorsListener(port);
    }
	
	public void run() {
		try {
			while(alive)
			{
				if(updateSensors()) {
					updateModel();
					sendActuators();	
				}
				else {
					Thread.sleep(5000);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean updateSensors() throws DeepHouseException {
		ArrayList<String> messages = sensorsListener.clearBuffer();
		for(String message : messages) {
			Frame frame = new Frame(message);
			house.updateSensor(frame);
		}
		return (!messages.isEmpty());
	}
	
	private void sendActuators() {
		for(Room r : house.getRooms()) {
			ActuatorSet actuators = r.getActuators();
	        Set<Map.Entry<String, Actuator>> set = actuators.entrySet();
	        for(Map.Entry<String,Actuator> entry : set) {
	        	if(entry.getValue().getModified()) {
	        		actuatorsSender.submitMessage(entry.getValue().getFrame());
	        		entry.getValue().setModified(false);
	        	}
	        }
		}
	}
	
	private void updateModel() {
		//TODO tout ce qui est IA
		//TODO enlever cet exemple et faire des vrais IAs
		for(Room r : house.getRooms()) {
			SensorSet sensors = r.getSensors();
			ActuatorSet actuators = r.getActuators();
			ArrayList<Sensor<Object>> temp = sensors.getByType(SensorType.TEMPERATURE);
			ArrayList<Actuator> heater = actuators.getByType(ActuatorType.RADIATOR);
			if(temp.size() != 0) {
				if((Double)temp.get(0).getLastValue() <= 20.0) {
					heater.get(0).setValue(23.0);
					heater.get(0).setModified(true);
				}
			}
		}
	}
	
    public void stopController() {
    	this.alive = false;
    	try {
	    	this.sensorsListener.stopListener();
	    	this.actuatorsSender.stopSender();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
	
}
