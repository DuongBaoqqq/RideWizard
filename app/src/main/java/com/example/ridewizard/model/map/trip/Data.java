package com.example.ridewizard.model.map.trip;

import java.util.List;

public class Data{
	private Route route;
	private Double distance;
	private List<OptionsItem> options;

	public Route getRoute(){
		return route;
	}

	public Double getDistance(){
		return distance;
	}

	public List<OptionsItem> getOptions(){
		return options;
	}
}