package com.blucode.mhmd.timeline.data;

import com.blucode.mhmd.timeline.ObjectBox;
import com.blucode.mhmd.timeline.data.model.AlbumMessage;
import com.blucode.mhmd.timeline.data.model.ImageMessage;
import com.blucode.mhmd.timeline.data.model.TextMessage;
import com.blucode.mhmd.timeline.data.model.VoiceMessage;

import java.util.List;

import io.objectbox.BoxStore;

public class AppDataManager implements DBHandler {
    private BoxStore boxStore;

    public AppDataManager() {
        boxStore = ObjectBox.get();
    }


    @Override
    public void insertAlbumMessage(AlbumMessage albumMessage) {
        boxStore.boxFor(AlbumMessage.class).put(albumMessage);
    }

    @Override
    public void removeAlbumMessage(AlbumMessage albumMessage) {
        boxStore.boxFor(AlbumMessage.class).remove(albumMessage);
    }

    @Override
    public List<AlbumMessage> getAllAlbumMessage() {
        return boxStore.boxFor(AlbumMessage.class).getAll();
    }

    @Override
    public void insertTextMessage(TextMessage textMessage) {
        boxStore.boxFor(TextMessage.class).put(textMessage);
    }

    @Override
    public void removeTextMessage(TextMessage textMessage) {
        boxStore.boxFor(TextMessage.class).remove(textMessage);
    }

    @Override
    public List<TextMessage> getAllTextMessage() {
        return boxStore.boxFor(TextMessage.class).getAll();
    }

    @Override
    public void insertVoiceMessage(VoiceMessage voiceMessage) {
        boxStore.boxFor(VoiceMessage.class).put(voiceMessage);
    }

    @Override
    public void removeVoiceMessage(VoiceMessage voiceMessage) {
        boxStore.boxFor(VoiceMessage.class).remove(voiceMessage);
    }

    @Override
    public List<VoiceMessage> getAllVoiceMessage() {
        return  boxStore.boxFor(VoiceMessage.class).getAll();
    }

    @Override
    public void insertImageMessage(ImageMessage imageMessage) {
        boxStore.boxFor(ImageMessage.class).put(imageMessage);
    }

    @Override
    public void removeImageMessage(ImageMessage imageMessage) {
        boxStore.boxFor(ImageMessage.class).remove(imageMessage);
    }

    @Override
    public List<ImageMessage> getAllImageMessage() {
       return boxStore.boxFor(ImageMessage.class).getAll();
    }
}
