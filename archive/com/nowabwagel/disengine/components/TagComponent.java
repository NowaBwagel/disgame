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
public class TagComponent extends Component {

    private String tag;

    public TagComponent(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
