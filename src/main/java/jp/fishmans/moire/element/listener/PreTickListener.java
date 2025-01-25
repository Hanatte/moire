package jp.fishmans.moire.element.listener;

@FunctionalInterface
public interface PreTickListener extends Listener {
    void onPreTick();
}
