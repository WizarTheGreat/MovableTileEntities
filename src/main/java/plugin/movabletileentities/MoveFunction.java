package plugin.movabletileentities;

import org.bukkit.block.TileState;

public interface MoveFunction<From, To> {

    MoveFunction<TileState, TileState> NULL = (f, t) -> {};

    void apply(From from, To to);
}
