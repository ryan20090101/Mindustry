package io.anuke.mindustry.world.blocks.types;

import io.anuke.mindustry.world.Tile;

public interface LogicAcceptor {

    /** Called on logic state change*/
    void onLogicChange(Tile source, Tile tile);

    /** Returns whether can link or no.*/
    boolean canLogicLink(Tile tile);

    /** Call on successful logicLink.*/
    void onLogicLink(Tile tile);

    /** Returns whether successful or not.*/
    boolean logicLink(Tile tile, Tile source);
}
