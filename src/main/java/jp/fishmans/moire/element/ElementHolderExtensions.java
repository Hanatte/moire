package jp.fishmans.moire.element;

import jp.fishmans.moire.element.listener.Listener;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface ElementHolderExtensions {
    <T extends Listener> void moire$addListener(T listener);

    <T extends Listener> void moire$removeListener(T listener);

    <T extends Listener> void moire$triggerEvent(Class<T> listenerClass, Consumer<T> consumer);
}
