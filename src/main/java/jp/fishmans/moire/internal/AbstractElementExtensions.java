package jp.fishmans.moire.internal;

import java.util.List;

@SuppressWarnings("unused")
public interface AbstractElementExtensions {
    List<? extends Runnable> moire$getTickListeners();

    void moire$addTickListener(Runnable runnable);

    int moire$getDuration();

    void moire$setDuration(int duration);

    int moire$getTickCount();

    void moire$setTickCount(int tickCount);
}
