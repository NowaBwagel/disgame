/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nowabwagel.disengine.components;

import com.jme3.math.Vector3f;
import com.nowabwagel.disengine.entitysystem.Component;

/**
 *
 * @author Noah Bergl
 */
public class MovementComponent extends Component{
    
    private Vector3f movement;
    
    public MovementComponent(Vector3f movement){
        this.movement = movement;
    }
    
    public Vector3f getMovement(){
        return movement;
    }
}
