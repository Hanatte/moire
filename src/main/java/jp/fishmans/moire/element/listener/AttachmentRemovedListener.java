package jp.fishmans.moire.element.listener;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;

@FunctionalInterface
public interface AttachmentRemovedListener extends Listener {
    void onAttachmentRemoved(HolderAttachment oldAttachment);
}
