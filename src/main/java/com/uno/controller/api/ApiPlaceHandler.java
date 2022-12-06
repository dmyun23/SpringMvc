package com.uno.controller.api;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.*;

@Component
public class ApiPlaceHandler {

    public ServerResponse getPlaces(ServerRequest req) {
        return ok().body(List.of("place1","place2"));
    }
    public ServerResponse createPlace(ServerRequest req) {
        return created(URI.create("/api/places/1")).body(true); // TODO : 1은 구현할 때 제대로 넣어주자
    }
    public ServerResponse getPlace(ServerRequest req) {
        return ok().body("place " + req.pathVariable("placeId"));
    }
    public ServerResponse modifyPlace(ServerRequest req){
        return ok().body(true);
    }
    public ServerResponse removePlace(ServerRequest req){
        return ok().body(true);
    }

}
