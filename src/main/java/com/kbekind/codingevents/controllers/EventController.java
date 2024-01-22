package com.kbekind.codingevents.controllers;

import com.kbekind.codingevents.data.EventData;
import com.kbekind.codingevents.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("events")
public class EventController {

//  public EventController() {
//    events.add("Menteaship A fun meetup for connecting with mentors");
//    events.add("Code With Pride A fun meetup sponsored by LaunchCode");
//    events.add("Javascripty An imaginary meetup for Javascript developers");
//  }
//
  @GetMapping
  public String displayAllEvents(Model model) {
    model.addAttribute("events", EventData.getAll());
    return "events/index";
  }

  @GetMapping("create")
  public String renderCreateEventForm(){
    return "events/create";
  }

  @PostMapping("create")
  public String processCreateEventForm(@ModelAttribute Event newEvent){

    EventData.add(newEvent);
    return "redirect:/events";
  }

  @GetMapping("delete")
  public String displayDeleteEventForm(Model model){
    model.addAttribute("title", "Delete Events");
    model.addAttribute("events", EventData.getAll());
    return "events/delete";
  }

  @PostMapping("delete")
  public String processDeleteEventForm(@RequestParam(required = false) int[] eventIds){
    for (int id: eventIds){
      EventData.remove(id);
    }
    return "redirect:/events";
  }

  @GetMapping("edit/{eventId}")
  public String displayEditForm(Model model, @PathVariable int eventId){

   model.addAttribute("event" ,EventData.getById(eventId));
    return "events/edit";

  }

  @PostMapping("edit")
  public String processEditForm(int eventId, String name, String description){

    Event editedEvent = EventData.getById(eventId);
    editedEvent.setName(name);
    editedEvent.setDescription(description);

    return "redirect:/events";

  }

}
