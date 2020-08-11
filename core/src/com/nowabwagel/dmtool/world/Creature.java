package com.nowabwagel.dmtool.world;

public class Creature {

    private String name;
    private int hp;
    private int maxHp;
    private String description;
    
    

    public Creature(String name, int hp, int maxHp, String description) {
	super();
	this.name = name;
	this.hp = hp;
	this.maxHp = maxHp;
	this.description = description;
    }

    public String getName() {
	return name;
    }

    public int getHp() {
	return hp;
    }

    public int getMaxHp() {
	return maxHp;
    }

    public String getDescription() {
	return description;
    }

}
