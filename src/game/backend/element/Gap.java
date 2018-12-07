package game.backend.element;

public class Gap extends Element {

    @Override
    public boolean isMovable() { return false; }

    @Override
    public String getKey() { return "GAP"; }
}
