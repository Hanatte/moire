package jp.fishmans.moire.internal;

import java.util.List;

@SuppressWarnings("unused")
public interface AbstractElementExtensions {
    List<? extends Runnable> moire$getTickListeners();

    void moire$addTickListener(Runnable runnable);
}
