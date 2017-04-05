/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nowabwagel.disengine.app.entitysystem;

/**
 *
 * @author Noah Bergl
 */
public class EntityIdGenerator {

    private long id;

    public EntityIdGenerator() {
        id = Long.MIN_VALUE;
    }

    public synchronized long getNextId() {
        id++;
        return id;
    }
}
