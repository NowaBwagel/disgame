package com.nowabwagel.openrpg.registry;

public interface GameRegistry<T> {
	
	public boolean register(String idenifier, T object);
}
