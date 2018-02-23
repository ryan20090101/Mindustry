package io.anuke.mindustry.resource;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Bundles;

public class Item{
	private static final Array<Item> items = new Array<>();

	public static final Item
		stone = new Item("stone"),
		iron = new Item("iron"),
		coal = new Item("coal"){{material=false;}},
		steel = new Item("steel"),
		titanium = new Item("titanium"),
		dirium = new Item("dirium"),
		uranium = new Item("uranium"){{material=false;}},
		sand = new Item("sand"),
<<<<<<< HEAD
        lead = new Item("lead");
		/*glass = new Item("glass"),
		silicon = new Item("silicon");*/
=======
		glass = new Item("glass"),
		silicon = new Item("silicon"),
		copper = new Item("copper"),
		tin = new Item("tin"),
		gravel = new Item("gravel"),
		dirt = new Item("dirt");
>>>>>>> d7b28aff44446d19af3356b1b0aaf7926d6c8435

	public final int id;
	public final String name;
	public TextureRegion region;
	public boolean material = true;
	public float explosiveness = 0f;
	public float flammability = 0f;

	public Item(String name) {
		this.id = items.size;
		this.name = name;

		items.add(this);
	}

	public void init(){
		this.region = Draw.region("icon-" + name);
	}

	public String localizedName(){
		return Bundles.get("item." + this.name + ".name");
	}

	@Override
	public String toString() {
		return localizedName();
	}

	public static Array<Item> getAllItems() {
		return Item.items;
	}

	public static Item getByID(int id){
		return items.get(id);
	}
}
