package com.blucode.mhmd.timeline.data;

import com.blucode.mhmd.timeline.data.model.AlbumMessage;
import com.blucode.mhmd.timeline.data.model.ImageMessage;
import com.blucode.mhmd.timeline.data.model.TextMessage;
import com.blucode.mhmd.timeline.data.model.VoiceMessage;

import java.util.List;

public interface DBHandler {

    void insertAlbumMessage(AlbumMessage albumMessage);

    void removeAlbumMessage(AlbumMessage albumMessage);

    List<AlbumMessage> getAllAlbumMessage();

    void insertTextMessage(TextMessage textMessage);

    void removeTextMessage(TextMessage textMessage);

    List<TextMessage> getAllTextMessage();

    void insertVoiceMessage(VoiceMessage voiceMessage);

    void removeVoiceMessage(VoiceMessage voiceMessage);

    List<VoiceMessage> getAllVoiceMessage();

    void insertImageMessage(ImageMessage imageMessage);

    void removeImageMessage(ImageMessage imageMessage);

    List<ImageMessage> getAllImageMessage();

}
