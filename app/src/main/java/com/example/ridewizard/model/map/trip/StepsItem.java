package com.example.ridewizard.model.map.trip;

public class StepsItem{
	private Duration duration;
	private StartLocation startLocation;
	private Distance distance;
	private String travelMode;
	private String htmlInstructions;
	private EndLocation endLocation;
	private String maneuver;
	private Polyline polyline;

	public Duration getDuration(){
		return duration;
	}

	public StartLocation getStartLocation(){
		return startLocation;
	}

	public Distance getDistance(){
		return distance;
	}

	public String getTravelMode(){
		return travelMode;
	}

	public String getHtmlInstructions(){
		return htmlInstructions;
	}

	public EndLocation getEndLocation(){
		return endLocation;
	}

	public String getManeuver(){
		return maneuver;
	}

	public Polyline getPolyline(){
		return polyline;
	}
}
