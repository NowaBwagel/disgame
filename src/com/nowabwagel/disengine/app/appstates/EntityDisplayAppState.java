package com.nowabwagel.disengine.app.appstates;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.nowabwagel.disengine.app.BaseApplication;
import com.nowabwagel.disengine.app.entitysystem.EntitySet;
import com.nowabwagel.disengine.app.stateengine.AbstractAppState;
import com.nowabwagel.disengine.app.stateengine.AppStateManager;

public class EntityDisplayAppState extends AbstractAppState {

	private BaseApplication app;
	
	private EntitySet entitySet;
	private AssetManager assetManager;
	
	private ModelBatch batch;

	@Override
	public void initialize(AppStateManager manager, BaseApplication application) {
		super.initialize(manager, application);

		app = application;
		assetManager = app.getAssetManager();
		entitySet = new EntitySet();
		
		batch = new ModelBatch();
	}

	@Override
	public void setEnable(boolean enabled) {
		// TODO Auto-generated method stub
		super.setEnable(enabled);
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return super.isEnabled();
	}

	@Override
	public void update(float tpf) {
		// TODO Auto-generated method stub
		super.update(tpf);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
	}

}
