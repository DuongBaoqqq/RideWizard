package com.example.ridewizard.model.map.trip;

import java.util.List;

public class Route{
	private String summary;
	private String copyrights;
	private List<LegsItem> legs;
	private List<Object> warnings;
	private Bounds bounds;
	private OverviewPolyline overviewPolyline;
	private List<Object> waypointOrder;

	public String getSummary(){
		return summary;
	}

	public String getCopyrights(){
		return copyrights;
	}

	public List<LegsItem> getLegs(){
		return legs;
	}

	public List<Object> getWarnings(){
		return warnings;
	}

	public Bounds getBounds(){
		return bounds;
	}

	public OverviewPolyline getOverviewPolyline(){
		return overviewPolyline;
	}

	public List<Object> getWaypointOrder(){
		return waypointOrder;
	}
}