package com.franzp.istisn.model;

import android.database.Cursor;

public class Quote {

	private Integer id;
	
	private String message;
	
	private String author;
	
	private String creationdate;
	
	private Integer nbTop;
	
	private Integer nbFlop;
	
	

	public Quote(Cursor cursor) {
		super();
		this.id = cursor.getInt(cursor.getColumnIndex("_id"));
		this.message = cursor.getString(cursor.getColumnIndex("message"));
		this.author = cursor.getString(cursor.getColumnIndex("author"));;
		this.creationdate = cursor.getString(cursor.getColumnIndex("creationdate"));;
		this.nbTop = cursor.getInt(cursor.getColumnIndex("nbTop"));
		this.nbFlop = cursor.getInt(cursor.getColumnIndex("nbFlop"));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	public Integer getNbTop() {
		return nbTop;
	}

	public void setNbTop(Integer nbTop) {
		this.nbTop = nbTop;
	}

	public Integer getNbFlop() {
		return nbFlop;
	}

	public void setNbFlop(Integer nbFlop) {
		this.nbFlop = nbFlop;
	}
}
