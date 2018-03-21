package io.anuke.mindustry.world.blocks.types;

import io.anuke.mindustry.world.Tile;

public interface LogicAcceptor {

    /** Returns if can source link.*/
    boolean canLogicOutput(Tile tile);

    /** Call on successful logicLink.*/
    void onLogicLink(Tile tile);

    /** Returns whether logic state change is successful or not, sets logic state on tile, source is setter.*/
    boolean setLogic(Tile tile, Tile source, Boolean logicState);

    /** Update output logic.*/
    void updateOutputLogic(Tile tile);

    /** Returns whether successful or not.*/
    boolean logicLink(Tile tile, Tile source);
}
