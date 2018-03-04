package io.anuke.mindustry.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import io.anuke.ucore.util.Bundles;

public class Structure{
	public int id = -1;
    public String name;
	public transient Pixmap pixmap;

	public Structure(){}
	
	public int getWidth(){
		return pixmap.getWidth();
	}
	
	public int getHeight(){
		return pixmap.getHeight();
	}
}
