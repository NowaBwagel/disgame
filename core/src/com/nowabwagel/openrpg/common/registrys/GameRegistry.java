package com.nowabwagel.openrpg.common.registrys;

public interface GameRegistry<T> {
	
	public boolean register(String idenifier, T object);
}
