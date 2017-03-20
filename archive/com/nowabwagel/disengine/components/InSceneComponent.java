package com.nowabwagel.disengine.components;

import com.nowabwagel.disengine.entitysystem.Component;

/**
 *
 * @author Noah Bergl
 */
public class InSceneComponent extends Component{
    
    private boolean value;
    
    public InSceneComponent(boolean value){
        this.value = value;
    }
    
    public boolean getValue(){
        return value;
    }
}
