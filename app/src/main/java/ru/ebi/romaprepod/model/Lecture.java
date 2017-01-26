package ru.ebi.romaprepod.model;

public class Lecture {

	public String title;
	public String description;
	public long date;

	public Lecture(String title, String description, long date) {
		this.title = title;
		this.description = description;
		this.date = date;
	}
}