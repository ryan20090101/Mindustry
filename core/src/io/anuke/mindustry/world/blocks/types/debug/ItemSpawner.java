package io.anuke.mindustry.world.blocks.types.debug;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.game.Inventory;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.resource.ItemStack;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.PowerBlock;
import io.anuke.ucore.scene.style.TextureRegionDrawable;
import io.anuke.ucore.scene.ui.ButtonGroup;
import io.anuke.ucore.scene.ui.ImageButton;
import io.anuke.ucore.scene.ui.TextField;
import io.anuke.ucore.scene.ui.layout.Table;

import static io.anuke.mindustry.Vars.state;
import static io.anuke.mindustry.Vars.world;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ItemSpawner extends Block{

    protected final int timerDump = timers++;
    protected final int timerSpawn = timers++;

    public ItemSpawner(String name) {
        super(name);
        update = true;
        solid = true;
    }

    @Override
    public void update(Tile tile) {
        ItemSpawnerEntity ent = tile.entity();

        if (ent.timer.get(timerSpawn,ent.spawnTime)) {
        for (int j = 0; j < 4; j++) {
            Tile other = tile.getNearby(j);
            if (other.block().acceptItem(ent.spawnItem,other,tile)) {
                offloadNear(tile, ent.spawnItem);
            }
        }}

        if (ent.timer.get(timerDump, 30)) {
            tryDump(tile, height - 1);
        }
    }

    @Override
    public void configure(Tile tile, byte data) {
        ItemSpawnerEntity entity = tile.entity();
        if(entity != null){
            entity.spawnItem = Item.getByID(data);
        }
    }

    @Override
    public void buildTable(Tile tile, Table table){
        ItemSpawnerEntity entity = tile.entity();

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
                entity.spawnItem = items.get(f);
                setConfigure(tile, (byte)f);
            }).size(38, 42).padBottom(-5.1f).group(group).get();
            button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(items.get(i).region));
            button.setChecked(entity.spawnItem.id == f);

            if(i%4 == 3){
                cont.row();
            }
        }

        TextField.TextFieldFilter filter = new TextField.TextFieldFilter.DigitsOnlyFilter();

        cont.row();

        cont.addField(Integer.toString(entity.spawnTime), filter ,text -> {
            if(text.isEmpty()) return;
            int pullTime = Integer.parseInt(text);
            if (pullTime < 0) return;
            entity.spawnTime = pullTime;
        }).size(152,42).padBottom(-5.1f).bottom().colspan(4);

        table.add(cont);
    }

    @Override
    public boolean isConfigurable(Tile tile){
        return true;
    }

    @Override
    public TileEntity getEntity(){
        return new ItemSpawnerEntity();
    }

    public static class ItemSpawnerEntity extends TileEntity{
        public Item spawnItem = Item.iron;
        public int spawnTime = 1;

        @Override
        public void write(DataOutputStream stream) throws IOException{
            stream.writeByte(spawnItem.id);
            stream.writeInt(spawnTime);
        }

        @Override
        public void read(DataInputStream stream) throws IOException{
            spawnItem = Item.getAllItems().get(stream.readByte());
            spawnTime = stream.readInt();
        }
    }
}