package com.kbekind.codingevents.models.dto;

import com.kbekind.codingevents.models.Event;
import com.kbekind.codingevents.models.Tag;
import jakarta.validation.constraints.NotNull;

public class EventTagDTO {

    @NotNull
    private Event event;

    @NotNull
    private Tag tag;

    public EventTagDTO() {}

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }

    public Tag getTag() { return tag; }

    public void setTag(Tag tag) { this.tag = tag; }

}
