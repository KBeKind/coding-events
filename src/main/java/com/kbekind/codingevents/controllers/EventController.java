package com.kbekind.codingevents.controllers;

import com.kbekind.codingevents.data.EventData;
import com.kbekind.codingevents.models.DaysOfWeek;
import com.kbekind.codingevents.models.Event;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
  public String renderCreateEventForm(Model model){
    model.addAttribute("daysOfWeek", DaysOfWeek.values());
    model.addAttribute(new Event());

    return "events/create";
  }

  @PostMapping("create")
  public String processCreateEventForm(@ModelAttribute @Valid Event newEvent, Errors errors, Model model){

    if(errors.hasErrors()){
      model.addAttribute("daysOfWeek", DaysOfWeek.values());
      return "events/create";

    }
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
  public String displayEditForm(Model model, @PathVariable(required = false) int eventId){

   model.addAttribute("event" ,EventData.getById(eventId));
    return "events/edit";

  }

  @PostMapping("edit")
  public String processEditForm(@ModelAttribute @Valid Event event, Errors errors, Model model){

    if (errors.hasErrors()) {
      model.addAttribute("event", event);
      return "events/edit";
    }

    Event editedEvent = EventData.getById(event.getId());

    if (editedEvent != null) {
      editedEvent.setName(event.getName());
      editedEvent.setDescription(event.getDescription());
      editedEvent.setContactEmail(event.getContactEmail());
      editedEvent.setLocation(event.getLocation());
      editedEvent.setAttendees(event.getAttendees());
      editedEvent.setHasRegistration(event.isHasRegistration());
    }

    return "redirect:/events";

  }

}
