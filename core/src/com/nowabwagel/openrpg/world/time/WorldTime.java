package com.nowabwagel.openrpg.world.time;

public interface WorldTime{

	/*
	 * Length of day in milli-seconds
	 */
	final long DAY_LENGTH = 1000 * 60 * 60 * 24;

	final long  TICK_EVENTS_PER_DAY = 64;
	
	final long TICK_EVENT_RATE = DAY_LENGTH / TICK_EVENTS_PER_DAY;
	
	long getMilliseconds();
	
	float getSeconds();
	
	float getDays();
	
	float getTimeRate();
	
	void setMilliseconds(long time);
	
	void setDays(float timeInDays);
	
}
