package de.goforfun.lorakid.pushover.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

@Data
@Builder
public class PushoverMessage {
    @NonNull
    private String token;

    @NonNull
    private String user;

    private String device;

    private String title;

    @NonNull
    private String message;

    private String attachment;
    private String attachment_base64;
    private String attachment_type;
    private Integer html = 0; // set to 1 to enable HTML parsing
    private Integer priority = 0; // a value of -2, -1, 0 (default), 1, or 2
    private String sound; // the name of a supported sound to override your default sound choice
    private Long timestamp = DateTime.now().getMillis(); // a Unix timestamp of a time to display instead of when our API received it
    private Long ttl = TimeUnit.HOURS.toSeconds(24); // a number of seconds that the message will live, before being deleted automatically
    private String url; // a supplementary URL to show with your message
    private String url_title; // a title for the URL specified as the url parameter, otherwise just the URL is shown
}
