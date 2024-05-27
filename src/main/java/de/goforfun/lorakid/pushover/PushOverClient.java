package de.goforfun.lorakid.pushover;

import de.goforfun.lorakid.pushover.model.PushOverApi;
import de.goforfun.lorakid.pushover.model.PushoverMessage;
import feign.Feign;
import feign.form.FormEncoder;
import feign.jackson.JacksonEncoder;

public class PushOverClient {

    private final String appToken;
    private final PushOverApi client;

    public PushOverClient(String targetUrl, String appToken) {
        super();
        this.appToken = appToken;
        this.client = Feign.builder()
                .encoder(new FormEncoder(new JacksonEncoder()))
                .target(PushOverApi.class, targetUrl);
    }

    public void sendMessage(String user, String messageText) {
        PushoverMessage message = PushoverMessage.builder()
                .token(this.appToken)
                .user(user)
                .message(messageText)
                .build();
        client.pushMessage(message);
    }
}
