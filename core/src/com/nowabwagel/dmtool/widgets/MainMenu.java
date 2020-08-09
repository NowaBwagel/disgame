package com.nowabwagel.dmtool.widgets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenu extends Container<Table>{

	private Skin skin;
	private Table table;


	public MainMenu(AssetManager assetManager) {
		super();
		this.top().center();
		this.setFillParent(true);
		table = new Table();
		this.setActor(table);
		skin = assetManager.get("data/skin.json", Skin.class);
		Label.LabelStyle title = new LabelStyle();

		Label title = new Label("DM Tools!", skin, "title");
		title.setFontScale(2);
		TextButton encounterButton = new TextButton("Encounter", skin);
		encounterButton.getLabel();
		table.row().grow();
		table.add(title);
		table.row().grow();
		table.add(encounterButton).maxSize(25);
		table.layout();
	}

}
