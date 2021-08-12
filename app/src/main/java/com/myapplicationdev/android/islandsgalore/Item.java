package com.myapplicationdev.android.islandsgalore;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Item implements Serializable {

	private int id;
	private String name;
	private String description;
	private int squarekm;
	private int stars;

    public Item(int id, String name, String description, int yearReleased, int stars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.squarekm = yearReleased;
        this.stars = stars;

    }

    public int getId() {
        return id;
    }

    public Item setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getSquarekm() {
        return squarekm;
    }

    public Item setSquarekm(int squarekm) {
        this.squarekm = squarekm;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public Item setStars(int stars) {
        this.stars = stars;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        String starsString = "";
        for(int i = 0; i < stars; i++){
            starsString += "*";
        }
        return name + "\n" + description + " - " + squarekm + "\n" + starsString;

    }
}
