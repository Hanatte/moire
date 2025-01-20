package jp.fishmans.moire.element.listener;

import net.minecraft.server.network.ServerPlayNetworkHandler;

@FunctionalInterface
public interface WatchingStartedListener extends Listener {
    void onWatchingStarted(ServerPlayNetworkHandler player);
}
