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
import com.h4313.deephouse.housemodel.RoomConstants;
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
		house = House.getInstance();
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
	
    @Override
	public void run() {
		try {
			while(alive)
			{
				if(updateSensors()) {
					System.out.println("Update Model");
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
			System.out.println("Message recu : " + message);
			Frame frame = new Frame(message);
			house.updateSensor(frame);
		}
		return (!messages.isEmpty());
	}
	
	private void sendActuators() {
		for(Room r : house.getRooms()) {
			Map<String,Actuator<Object>> actuators = r.getActuators();
	        Set<Map.Entry<String, Actuator<Object>>> set = actuators.entrySet();
	        for(Map.Entry<String,Actuator<Object>> entry : set) {
	        	if(entry.getValue().getModified()) {
	        		System.out.println("Envoye : " + entry.getValue().getFrame());
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
			ArrayList<Sensor<Object>> light = r.getSensorByType(SensorType.LIGHT);
			ArrayList<Actuator<Object>> lightcontrol = r.getActuatorByType(ActuatorType.LIGHTCONTROL);
			if(light.size() != 0 && r.getIdRoom() == RoomConstants.ID_BEDROOM) {
				System.out.println("Il y a une lumiere dans " + r);
				System.out.println("Valeur : " + ((Boolean)light.get(0).getLastValue()).booleanValue());
				if(((Boolean)light.get(0).getLastValue()).booleanValue()) {
					System.out.println("La lumiere est allumee");
					lightcontrol.get(0).setLastValue(false);
					lightcontrol.get(0).setModified(true);
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
