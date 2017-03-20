package com.nowabwagel.disengine.entitysystem;

/**
 *
 * @author Noah Bergl
 */
public interface EntityEventListener {

    public void receiveEntityEvent(EntityEvent changeEvent);

    public Class<? extends Component>[] componentsIntrestedIn();
}
