package com.uno.dto;

import com.uno.constance.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceDto {

    private PlaceType placeType;
    private String placeName;
    private String address;
    private String phoneNumber;
    private Integer capacity;
    private String memo;


    public static PlaceDto of(PlaceType placeType, String placeName, String address,
                              String phoneNumber, Integer capacity, String memo)
    {
        return new PlaceDto(placeType, placeName, address, phoneNumber, capacity, memo);
    }

}
