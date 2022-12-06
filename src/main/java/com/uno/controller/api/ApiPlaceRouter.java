package com.uno.controller.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

import static org.springframework.web.servlet.function.RequestPredicates.*;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class ApiPlaceRouter {


    @Bean
    public RouterFunction<ServerResponse> placeRouter(ApiPlaceHandler apiPlaceHandler){
        return route().nest(path("/api/places"), builder -> builder
                .GET("",apiPlaceHandler::getPlaces)
                .POST("",apiPlaceHandler::createPlace)
                .GET("/{placeId}",apiPlaceHandler::getPlace)
                .PUT("/{placeId}",apiPlaceHandler::modifyPlace)
                .DELETE("/{placeId}",apiPlaceHandler::removePlace)
        ).build();

    }

}
