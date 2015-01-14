package com.malek.hamid.handlers;

import java.util.ArrayList;
import java.util.List;

import com.malek.hamid.Food;

public class Group {
	 private String name;
	 private int id;
	 
	  public final List<Food> children = new ArrayList<Food>();

	  public Group(String string, int id) {
	    this.setName(string);
	    this.setId(id);
	  }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
