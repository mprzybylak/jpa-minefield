package com.mprzybylak.minefields.jpa.id.base;

public interface SampleEntity<T> {
	
	T getId();
	void setText(String text);
	String getText();
}
