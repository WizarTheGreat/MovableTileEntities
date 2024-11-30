package plugin.movabletileentities;


public record Pair<A extends Class<?>>(A first, MoveFunction second) {

}