package com.uno.controller.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestMapping("/api")
//@RestController
public class ApiPlaceController {

    @GetMapping("/places")
    public List<String> getPlaces() {
        return List.of("place1", "place2");
    }
    @PostMapping("/places")
    public Boolean createPlace(){
        return true;
    }
    @GetMapping("/places/{placeId}")
    public String getPlace(@PathVariable Integer eventId) {
        return "event " +eventId;
    }
    @PutMapping("/places/{placeId}")
    public Boolean modifyPlace(@PathVariable Integer eventId){
        return true;
    }
    @DeleteMapping("/places/{placeId}")
    public Boolean removePlace(@PathVariable Integer eventId){
        return true;
    }
}
