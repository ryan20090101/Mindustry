package io.anuke.mindustry.resource;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import io.anuke.ucore.util.Bundles;

public class Liquid {

	private static final Array<Liquid> liquids = new Array<>();

	public static final Liquid
		water = new Liquid("water", Color.ROYAL,20),
		plasma = new Liquid("plasma", Color.CORAL,99999999),
		lava = new Liquid("lava", Color.valueOf("ed5334"),650),
		oil = new Liquid("oil", Color.valueOf("292929"),43),
		steam = new Liquid("steam", Color.valueOf("c4c6c4"),100),
		cryofluid = new Liquid("cryofluid", Color.SKY,-100);
	
	public final Color color;
	public final String name;
	public final int id;
	public final int heat;
	
	public Liquid(String name, Color color,int heat) {
		this.name = name;
		this.color = new Color(color);
		this.heat = heat;

		this.id = liquids.size;

		Liquid.liquids.add(this);
	}

	public String localizedName(){
		return Bundles.get("liquid."+ this.name + ".name");
	}

	@Override
	public String toString(){
		return localizedName();
	}

	public static Array<Liquid> getAllLiquids() {
		return Liquid.liquids;
	}

	public static Liquid getByID(int id){
		return liquids.get(id);
	}
}
