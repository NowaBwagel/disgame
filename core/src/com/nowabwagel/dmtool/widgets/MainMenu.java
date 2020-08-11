package com.nowabwagel.dmtool.widgets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.nowabwagel.dmtool.Globals;
import com.nowabwagel.dmtool.MySkin;
import com.nowabwagel.dmtool.world.Creature;

public class MainMenu extends Container<Table> {

    private MainMenu menu;
    private AssetManager assetManager;
    private Skin skin;
    private Table table;

    public MainMenu() {
	super();
	menu = this;
	assetManager = Globals.assetManager;
	this.top().center();
	this.setFillParent(true);
	table = new Table();
	this.setActor(table);
	skin = assetManager.get("data/skin.json", MySkin.class);

	Label title = new Label("DM Tools!", skin, "title");
	TextButton encounterButton = new TextButton("Encounter", skin, "red");
	encounterButton.getLabel();
	encounterButton.addListener(new EventListener() {

	    @Override
	    public boolean handle(Event event) {
		menu.setVisible(false);
		menu.getParent().addActor(new CreatureInfoWidget(new Creature("Test", 100, 75, "This is to be a descriptiionfor a craeture to be thingsys")));
		return true;
	    }
	});
	table.row().grow();
	table.add(title);
	table.row().grow();
	table.add(encounterButton).maxSize(25);
	table.layout();
    }

}
