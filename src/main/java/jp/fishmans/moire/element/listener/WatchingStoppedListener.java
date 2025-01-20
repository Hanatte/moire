package jp.fishmans.moire.element.listener;

import net.minecraft.server.network.ServerPlayNetworkHandler;

@FunctionalInterface
public interface WatchingStoppedListener extends Listener {
    void onWatchingStopped(ServerPlayNetworkHandler player);
}
