package io.anuke.mindustry.world.blocks.types.distribution;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.game.Inventory;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.resource.ItemStack;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.scene.style.TextureRegionDrawable;
import io.anuke.ucore.scene.ui.ButtonGroup;
import io.anuke.ucore.scene.ui.ImageButton;
import io.anuke.ucore.scene.ui.layout.Table;

import static io.anuke.mindustry.Vars.state;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CorePuller extends Block{

	protected final int timerDump = timers++;
	
	public CorePuller(String name) {
		super(name);
		update = true;
		solid = true;
	}

	@Override
	public void update(Tile tile) {
		CorePullerEntity ent = tile.entity();

		for(int j = 0; j < 4; j ++) {
			Tile other = tile.getNearby(j);
			if (other != null && other.block().acceptItem(ent.sortItem, other, tile)) {
				if (state.inventory.hasItem(new ItemStack(ent.sortItem,1))) {
					state.inventory.removeItem(new ItemStack(ent.sortItem,1));
					offloadNear(tile, ent.sortItem);
				}

			}
		}

		if(ent.timer.get(timerDump, 30)){
			tryDump(tile);
		}
	}

	@Override
	public void configure(Tile tile, byte data) {
		CorePullerEntity entity = tile.entity();
		if(entity != null){
			entity.sortItem = Item.getByID(data);
		}
	}

	@Override
	public void buildTable(Tile tile, Table table){
		CorePullerEntity entity = tile.entity();

		Array<Item> items = Item.getAllItems();

		ButtonGroup<ImageButton> group = new ButtonGroup<>();
		Table cont = new Table();
		cont.margin(4);
		cont.marginBottom(5);

		cont.add().colspan(4).height(105f);
		cont.row();

		for(int i = 0; i < items.size; i ++){
			final int f = i;
			ImageButton button = cont.addImageButton("white", "toggle", 24, () -> {
				entity.sortItem = items.get(f);
				setConfigure(tile, (byte)f);
			}).size(38, 42).padBottom(-5.1f).group(group).get();
			button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(items.get(i).region));
			button.setChecked(entity.sortItem.id == f);

			if(i%4 == 3){
				cont.row();
			}
		}

		table.add(cont);
	}

	@Override
	public boolean isConfigurable(Tile tile){
		return true;
	}
	
	@Override
	public TileEntity getEntity(){
		return new CorePullerEntity();
	}

	public static class CorePullerEntity extends TileEntity{
		public Item sortItem = Item.iron;
		
		@Override
		public void write(DataOutputStream stream) throws IOException{
			stream.writeByte(sortItem.id);
		}
		
		@Override
		public void read(DataInputStream stream) throws IOException{
			sortItem = Item.getAllItems().get(stream.readByte());
		}
	}
}