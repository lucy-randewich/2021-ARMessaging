package com.ajal.arsocialmessaging.ui.home.common.helpers;

public class Banner {
    private int messageId;
    private String albedoTexture;
    private String pbrTexture;
    private String filename;


    public Banner(int messageId) {
        switch (messageId) {
            case 0:
                this.messageId = messageId;
                this.albedoTexture = "models/happy-birthday.png";
                this.pbrTexture = "models/white_texture.png";
                this.filename = "models/happy-birthday.obj";
        }
    }

    public int getMessageId() {
        return messageId;
    }

    public String getAlbedoTexture() {
        return this.albedoTexture;
    }

    public String getPbrTexture() {
        return this.pbrTexture;
    }

    public String getFilename() {
        return this.filename;
    }
}
