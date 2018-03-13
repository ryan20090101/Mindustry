package io.anuke.mindustry.world.blocks.types.distribution;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.PowerBlock;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.scene.ui.ButtonGroup;
import io.anuke.ucore.scene.ui.ImageButton;
import io.anuke.ucore.scene.ui.TextField.TextFieldFilter;
import io.anuke.ucore.scene.ui.layout.Table;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Strings;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static io.anuke.mindustry.Vars.syncBlockState;

public class Teleporter extends PowerBlock{
	public static final int channels = 256; //maximum limit for Byte
	private static byte lastChannel = 0;

	private static ObjectSet<Tile>[] teleporters = new ObjectSet[channels];

	private Array<Tile> removal = new Array<>();
	private Array<Tile> returns = new Array<>();

	protected float powerPerItem = 0.8f;

	static{
		for(int i = 0; i < channels; i ++){
			teleporters[i] = new ObjectSet<>();
		}
	}
	
	public Teleporter(String name) {
		super(name);
		update = true;
		solid = true;
		health = 80;
		powerCapacity = 30f;
		instantTransfer = true;
	}

	@Override
	public void placed(Tile tile){
		tile.<TeleporterEntity>entity().channel = lastChannel;
		Timers.run(1f, () -> setConfigure(tile, lastChannel));
	}

	@Override
	public void configure(Tile tile, byte... data) {
		TeleporterEntity entity = tile.entity();
		if(entity != null){
			entity.channel = data[0];
		}
	}

	@Override
	public void getStats(Array<String> list){
		super.getStats(list);
		list.add("[powerinfo]Power/item: " + Strings.toFixed(powerPerItem, 1));
	}
	
	@Override
	public void draw(Tile tile){
		TeleporterEntity ent = tile.entity();

		super.draw(tile);

		Draw.color(new Color(ent.channel));
		Draw.rect("blank", tile.worldx(), tile.worldy(), 2, 2);
		Draw.color(Color.WHITE);
		Draw.alpha(0.45f + Mathf.absin(Timers.time(), 7f, 0.26f));
		Draw.rect("teleporter-top", tile.worldx(), tile.worldy());
		Draw.reset();
	}
	
	@Override
	public void update(Tile tile){
		TeleporterEntity entity = tile.entity();

		teleporters[entity.channel & 0xFF].add(tile);

		if(entity.totalItems() > 0){
			tryDump(tile);
		}
	}

	@Override
	public boolean isConfigurable(Tile tile){
		return true;
	}
	
	@Override
	public void buildTable(Tile tile, Table table){
		TeleporterEntity entity = tile.entity();

		ButtonGroup<ImageButton> group = new ButtonGroup<>();
		Table cont = new Table();
		cont.margin(4);
		cont.marginBottom(5);

		cont.add().colspan(4).height(105f);
		cont.row();

		TextFieldFilter.DigitsOnlyFilter filter = new TextFieldFilter.DigitsOnlyFilter();

		cont.addField(Integer.toString(entity.channel&0xFF), filter, text -> {
			if(text.isEmpty()) return;
			int chan = Integer.parseInt(text);
			if (chan > channels-1) return;
			if (chan < 0) return;
			lastChannel = (byte) chan;
			entity.channel = (byte) chan;
			setConfigure(tile, (byte) chan);
		}).grow().pad(8);

		table.add(cont);
	}
	
	@Override
	public void handleItem(Item item, Tile tile, Tile source){
		PowerEntity entity = tile.entity();

		Array<Tile> links = findLinks(tile);
		
		if(links.size > 0){
            if(!syncBlockState || Net.server() || !Net.active()){
                Tile target = links.random();
                target.entity.addItem(item, 1);
            }
		}

		entity.power -= powerPerItem;
	}
	
	@Override
	public boolean acceptItem(Item item, Tile tile, Tile source){
		PowerEntity entity = tile.entity();
		return !(source.block() instanceof Teleporter) && entity.power >= powerPerItem && findLinks(tile).size > 0;
	}
	
	@Override
	public TileEntity getEntity(){
		return new TeleporterEntity();
	}
	
	private Array<Tile> findLinks(Tile tile){
		TeleporterEntity entity = tile.entity();
		
		removal.clear();
		returns.clear();
		
		for(Tile other : teleporters[entity.channel]){
			if(other != tile){
				if(other.block() instanceof Teleporter){
					if(other.<TeleporterEntity>entity().channel != entity.channel){
						removal.add(other);
					}else if(other.entity.totalItems() == 0){
						returns.add(other);
					}
				}else{
					removal.add(other);
				}
			}
		}

		for(Tile remove : removal)
			teleporters[entity.channel].remove(remove);
		
		return returns;
	}

	public static class TeleporterEntity extends PowerEntity{
		public byte channel = 0;
		
		@Override
		public void write(DataOutputStream stream) throws IOException{
			stream.writeByte(channel);
		}
		
		@Override
		public void read(DataInputStream stream) throws IOException{
			channel = stream.readByte();
		}
	}

}
