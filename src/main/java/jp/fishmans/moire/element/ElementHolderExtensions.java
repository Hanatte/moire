package jp.fishmans.moire.element;

import jp.fishmans.moire.element.listener.Listener;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface ElementHolderExtensions {
    <T extends Listener> void moire$addListener(Class<T> listenerClass, T listener);

    <T extends Listener> void moire$removeListener(Class<T> listenerClass, T listener);

    <T extends Listener> void moire$triggerEvent(Class<T> listenerClass, Consumer<T> consumer);
}
