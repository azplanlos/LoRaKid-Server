package de.goforfun.lorakid.pushover.model;

import feign.Headers;
import feign.RequestLine;

public interface PushOverApi {
    @RequestLine("POST /1/messages.json")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    void pushMessage (PushoverMessage message);
}
