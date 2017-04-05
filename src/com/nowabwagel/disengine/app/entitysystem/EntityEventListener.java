package com.nowabwagel.disengine.app.entitysystem;

/**
 *
 * @author Noah Bergl
 */
public interface EntityEventListener {

    public void receiveEntityEvent(EntityEvent changeEvent);

    public Class<? extends DataComponent>[] componentsIntrestedIn();
}
