package com.example.ridewizard.model.map.trip;

public class StepsItem{
	private Duration duration;
	private StartLocation start_location;
	private Distance distance;
	private String travel_mode;
	private String html_instructions;
	private EndLocation end_location;
	private String maneuver;
	private Polyline polyline;

	public Duration getDuration(){
		return duration;
	}


	public Distance getDistance(){
		return distance;
	}

	public String getManeuver(){
		return maneuver;
	}

	public Polyline getPolyline(){
		return polyline;
	}

	public StartLocation getStart_location() {
		return start_location;
	}

	public String getTravel_mode() {
		return travel_mode;
	}

	public String getHtml_instructions() {
		return html_instructions;
	}

	public EndLocation getEnd_location() {
		return end_location;
	}
}
