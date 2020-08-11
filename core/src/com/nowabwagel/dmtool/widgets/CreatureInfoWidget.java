package com.nowabwagel.dmtool.widgets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nowabwagel.dmtool.Globals;
import com.nowabwagel.dmtool.world.Creature;

public class CreatureInfoWidget extends Container<Table> {

    AssetManager assetManager;
    Table container;

    Label name;
    Label maxHP;
    Label hp;
    Label description;

    Creature creature;

    public CreatureInfoWidget(Creature creature) {
	super();
	this.creature = creature;
	assetManager = Globals.assetManager;

	container = new Table(Globals.skin);
	this.setActor(container);

	name = new Label(creature.getName(), Globals.skin);
	maxHP = new Label(Integer.toString(creature.getMaxHp()), Globals.skin);
	hp = new Label(Integer.toString(creature.getHp()), Globals.skin);
	description = new Label(creature.getDescription(), Globals.skin);
	description.setWrap(true);
	
	container.row().grow();
	container.add(name);
	container.row().grow();
	container.add(hp);
	container.add("/");
	container.add(maxHP);
	container.layout();
    }

}
