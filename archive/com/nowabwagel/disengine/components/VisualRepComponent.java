/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nowabwagel.disengine.components;

import com.nowabwagel.disengine.entitysystem.Component;

/**
 *
 * @author Noah Bergl
 */
public class VisualRepComponent extends Component {
    
    private String assetName;
    
    public VisualRepComponent(String assetName){
        this.assetName = assetName;
    }
    
    public String getAssetName(){
        return assetName;
    }
    
}
