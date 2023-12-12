package com.example.ridewizard.model.map.trip;

import java.util.List;

public class Data{
	private Route route;
	private Object distance;
	private List<OptionsItem> options;

	public Route getRoute(){
		return route;
	}

	public Object getDistance(){
		return distance;
	}

	public List<OptionsItem> getOptions(){
		return options;
	}
}