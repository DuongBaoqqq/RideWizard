package com.example.ridewizard.model.map.trip;

import java.util.List;

public class Route{
	private String summary;
	private String copyrights;
	private List<LegsItem> legs;
	private List<Object> warnings;
	private Bounds bounds;
	private OverviewPolyline overview_polyline;
	private List<Object> waypoint_order;

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

	public OverviewPolyline getOverview_polyline() {
		return overview_polyline;
	}

	public List<Object> getWaypoint_order() {
		return waypoint_order;
	}
}