package old.nowabwagel.openrpg.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class VisualComponent implements Component{
	public ModelInstance modelInstance = null;
	public boolean linkedTransform = false;
}
