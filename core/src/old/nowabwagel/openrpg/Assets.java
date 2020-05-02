package old.nowabwagel.openrpg;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Logger;

public class Assets {
	public static Assets inst;
	public static AssetManager manager;

	public Assets() {
		inst = this;
		manager = new AssetManager();
		manager.setErrorListener(new AssetErrorListener() {
			@Override
			public void error(AssetDescriptor assetDescriptor, Throwable throwable) {
				System.err.println(assetDescriptor.toString());
				System.err.println(throwable.getMessage());
			}
		});
		manager.getLogger().setLevel(Logger.DEBUG);

		manager.load("Models/BigRock1.g3db", Model.class);
	}
}
