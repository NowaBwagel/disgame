package com.nowabwagel.openrpg.world.time;

import java.util.concurrent.atomic.AtomicLong;

import com.badlogic.ashley.core.EntitySystem;

public class WorldTimeImpl extends EntitySystem implements WorldTime {

	private static final float WORLD_TIME_MULTIPLIER = 24f;

	private AtomicLong worldTime = new AtomicLong();

	@Override
	public void update(float deltaTime) {
		long deltaMs = (long) (deltaTime * 1000);
		if (deltaMs > 0) {
			deltaMs = (long) (deltaMs * WORLD_TIME_MULTIPLIER);
			worldTime.getAndAdd(deltaMs);
		}
	}

	@Override
	public long getMilliseconds() {
		return worldTime.get();
	}

	@Override
	public float getSeconds() {
		return getMilliseconds() / 1000;
	}

	@Override
	public float getDays() {
		return getMilliseconds() / (float) DAY_LENGTH;
	}

	@Override
	public float getTimeRate() {
		return WORLD_TIME_MULTIPLIER;
	}

	@Override
	public void setMilliseconds(long time) {
		worldTime.getAndSet(time);

	}

	@Override
	public void setDays(float timeInDays) {
		setMilliseconds((long) ((double) timeInDays * DAY_LENGTH));
	}

}
