package com.example.ridewizard.model.map.trip;

import java.util.List;

public class LegsItem{
	private Duration duration;
	private StartLocation startLocation;
	private Distance distance;
	private String startAddress;
	private EndLocation endLocation;
	private String endAddress;
	private List<Object> viaWaypoint;
	private List<StepsItem> steps;
	private List<Object> trafficSpeedEntry;

	public Duration getDuration(){
		return duration;
	}

	public StartLocation getStartLocation(){
		return startLocation;
	}

	public Distance getDistance(){
		return distance;
	}

	public String getStartAddress(){
		return startAddress;
	}

	public EndLocation getEndLocation(){
		return endLocation;
	}

	public String getEndAddress(){
		return endAddress;
	}

	public List<Object> getViaWaypoint(){
		return viaWaypoint;
	}

	public List<StepsItem> getSteps(){
		return steps;
	}

	public List<Object> getTrafficSpeedEntry(){
		return trafficSpeedEntry;
	}
}