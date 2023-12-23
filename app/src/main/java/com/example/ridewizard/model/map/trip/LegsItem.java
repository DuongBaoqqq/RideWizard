package com.example.ridewizard.model.map.trip;

import java.util.List;

public class LegsItem{
	private Duration duration;
	private StartLocation start_location;
	private Distance distance;
	private String start_address;
	private EndLocation end_location;
	private String end_address;
	private List<Object> via_waypoint;
	private List<StepsItem> steps;
	private List<Object> traffic_speed_entry;

	public Duration getDuration(){
		return duration;
	}


	public Distance getDistance(){
		return distance;
	}

	public List<StepsItem> getSteps(){
		return steps;
	}

	public StartLocation getStart_location() {
		return start_location;
	}

	public String getStart_address() {
		return start_address;
	}

	public EndLocation getEnd_location() {
		return end_location;
	}

	public String getEnd_address() {
		return end_address;
	}

	public List<Object> getVia_waypoint() {
		return via_waypoint;
	}

	public List<Object> getTraffic_speed_entry() {
		return traffic_speed_entry;
	}
}