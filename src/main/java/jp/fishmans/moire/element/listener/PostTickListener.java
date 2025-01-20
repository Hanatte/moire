package jp.fishmans.moire.element.listener;

@FunctionalInterface
public interface PostTickListener extends Listener {
    void onTick();
}
