package io.anuke.mindustry.game;

import io.anuke.arc.Core;
import io.anuke.arc.func.Cons;
import io.anuke.arc.math.geom.Vector2;
import io.anuke.mindustry.content.Blocks;
import io.anuke.mindustry.entities.type.TileEntity;
import io.anuke.mindustry.type.Item;
import io.anuke.mindustry.type.ItemType;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.sandbox.ItemSource.ItemSourceEntity;
import io.anuke.mindustry.world.modules.ItemModule;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static io.anuke.mindustry.Vars.*;

public class Auto {
    public boolean enabled = true;
    private Field itemSourceEntityOutputItemField;
    public Tile targetItemSource;

    public class Context {
        public List<String> args;

        public Context(List<String> args) {
            this.args = args;
        }
    }

    public HashMap<String, Cons<Context>> commands = new HashMap<>();

    public Auto() {
        addCommand("autoitemsource", this::autoItemSourceCommand);
        try {
            Class<ItemSourceEntity> itemSourceEntityClass = ItemSourceEntity.class;
            itemSourceEntityOutputItemField = itemSourceEntityClass.getDeclaredField("outputItem");
            itemSourceEntityOutputItemField.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException("reflective access failed on ItemSourceEntity.outputItem");
        }
    }

    public void update() {
        if (!enabled) return;
        updateItemSourceTracking();
    }

    public void updateItemSourceTracking() {
        if (targetItemSource == null) return;
        if (targetItemSource.block() != Blocks.itemSource) {
            targetItemSource = null;
            return;
        }
        TileEntity core = player.getClosestCore();
        if (core == null) return;
        ItemModule items = core.items;

        Item least = null;
        int count = Integer.MAX_VALUE;
        for (int i = 0; i < content.items().size; i++) {
            Item currentItem = content.item(i);
            if (currentItem.type != ItemType.material) continue;
            int currentCount = items.get(currentItem);
            if (currentCount < count) {
                least = currentItem;
                count = currentCount;
            }
        }
        ItemSourceEntity entity = targetItemSource.ent();
        Item currentConfigured;
        try {
            currentConfigured = (Item)itemSourceEntityOutputItemField.get(entity);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("reflective access failed on ItemSourceEntity.outputItem");
        }
        if (least != null && least != currentConfigured) targetItemSource.configure(least.id);
    }

    public void addCommand(String name, Cons<Context> handler) {
        commands.put(name, handler);
    }

    public boolean runCommand(String message) {
        if (!message.startsWith("/")) return false;
        String[] args = message.split(" ");
        args[0] = args[0].substring(1);
        Cons<Context> command = commands.get(args[0].toLowerCase());
        if (command == null) return false;
        command.get(new Context(Arrays.asList(args)));
        return true;
    }

    public Tile getCursorTile() {
        Vector2 vec = Core.input.mouseWorld(Core.input.mouseX(), Core.input.mouseY());
        return world.tile(world.toTile(vec.x), world.toTile(vec.y));
    }

    public void reply(String message) {
        ui.chatfrag.addMessage(message, null);
    }

    public void autoItemSourceCommand(Context ctx) {
        if (ctx.args.size() == 2) {
            if (ctx.args.get(1).toLowerCase().equals("cancel")) {
                targetItemSource = null;
                reply("cancelled automatic item source configuration");
                return;
            }
        }
        Tile tile = getCursorTile();
        if (tile == null) {
            reply("cursor is not on a tile");
            return;
        }
        if (tile.block() != Blocks.itemSource) {
            reply("target tile is not an item source");
            return;
        }
        targetItemSource = tile;
        reply("automatically configuring item source (" + tile.x + ", " + tile.y + ")");
    }
}
