package jp.fishmans.moire.element.listener;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface AttachmentSetListener extends Listener {
    void onAttachmentSet(HolderAttachment attachment, @Nullable HolderAttachment oldAttachment);
}
