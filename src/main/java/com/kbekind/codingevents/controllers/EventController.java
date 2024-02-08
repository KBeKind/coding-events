package com.kbekind.codingevents.controllers;

import com.kbekind.codingevents.data.EventCategoryRepository;
import com.kbekind.codingevents.data.EventRepository;
import com.kbekind.codingevents.data.TagRepository;
import com.kbekind.codingevents.models.Event;
import com.kbekind.codingevents.models.EventCategory;
import com.kbekind.codingevents.models.EventDetails;
import com.kbekind.codingevents.models.Tag;
import com.kbekind.codingevents.models.dto.EventTagDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private EventCategoryRepository eventCategoryRepository;

  @Autowired
  private TagRepository tagRepository;

  @GetMapping
  public String displayAllEvents(Model model, @RequestParam(required = false) Integer categoryId) {

    if(categoryId == null){
      model.addAttribute("events", eventRepository.findAll());
      model.addAttribute("title", "All Events");
    } else {
      Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
      if(result.isEmpty()){
        model.addAttribute("title", "Invalid Category ID");
      }else{
        EventCategory eventCategory = result.get();
        model.addAttribute("title", "Events in specific category");
        model.addAttribute("events", eventCategory.getEvents());
      }
    }
    return "events/index";
  }

  @GetMapping("create")
  public String displayCreateEventForm(Model model) {
    model.addAttribute("title", "Create Event");
    model.addAttribute(new Event());
    model.addAttribute("categories", eventCategoryRepository.findAll());
    return "events/create";
  }

  @PostMapping("create")
  public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                       Errors errors, Model model) {
    if(errors.hasErrors()) {
      model.addAttribute("title", "Create Event");
      model.addAttribute("categories", eventCategoryRepository.findAll());
      return "events/create";
    }

    eventRepository.save(newEvent);
    return "redirect:/events";
  }

  @GetMapping("delete")
  public String displayDeleteEventForm(Model model) {
    model.addAttribute("title", "Delete Events");
    model.addAttribute("events", eventRepository.findAll());
    return "events/delete";
  }

  @PostMapping("delete")
  public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {

    if (eventIds != null) {
      for (int id : eventIds) {
        eventRepository.deleteById(id);
      }
    }

    return "redirect:/events";
  }

  @GetMapping("detail")
  public String displayEventDetails(@RequestParam Integer eventId, Model model) {

    Optional<Event> result = eventRepository.findById(eventId);

    if (result.isEmpty()) {
      model.addAttribute("title", "Invalid Event ID: " + eventId);
    } else {
      Event event = result.get();
      model.addAttribute("title", event.getName() + " Details");
      model.addAttribute("event", event);
    }

    return "events/detail";
  }

  @GetMapping("add-tag")
  public String displayAddTagForm(@RequestParam Integer eventId, Model model){

    Optional<Event> result = eventRepository.findById(eventId);
    Event event = result.get();
    EventTagDTO eventTagDTO = new EventTagDTO();
    eventTagDTO.setEvent(event);

    model.addAttribute("Title", "add tag to event");
    model.addAttribute("tags", tagRepository.findAll());
    model.addAttribute("event", event);
    model.addAttribute("eventTag", eventTagDTO);

    return "events/add-tag";

  }

  @PostMapping("add-tag")
  public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTagDTO, Errors errors, Model model){

    if(!errors.hasErrors()){
      Event event = eventTagDTO.getEvent();
      Tag tag = eventTagDTO.getTag();

      if(!event.getTags().contains(tag)){
        event.addTag(tag);
        eventRepository.save(event);
      }
      return "redirect:/events/detail?eventId=" + event.getId();
    }
    return "redirect:/events/add-tag";
  }

}
