package com.uno.controller.api;

import com.uno.constance.PlaceType;
import com.uno.dto.ApiDataResponse;
import com.uno.dto.PlaceDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiPlaceController {

//    @GetMapping("/places")
//    public ApiDataResponse<List<PlaceDto>> getPlaces() {
//        return ApiDataResponse.of(List.of(PlaceDto.of(
//                PlaceType.COMMON,
//                "풋살장",
//                "서울시 서대문구 홍제동",
//                "010-5249-3111",
//                30,
//                "신장개업"
//        )));
//    }
//    @PostMapping("/places")
//    public Boolean createPlace(){
//        return true;
//    }
//    @GetMapping("/places/{placeId}")
//    public ApiDataResponse<PlaceDto> getPlace(@PathVariable Integer placeId) {
//        if(placeId==2){
//            return ApiDataResponse.of(null);
//        }
//        return ApiDataResponse.of(PlaceDto.of(
//                PlaceType.COMMON,
//                "풋살장",
//                "서울시 서대문구 홍제동",
//                "010-5249-3111",
//                30,
//                "신장개업"
//        ));
//    }
    @PutMapping("/places/{placeId}")
    public Boolean modifyPlace(@PathVariable Integer placeId){
        return true;
    }
    @DeleteMapping("/places/{placeId}")
    public Boolean removePlace(@PathVariable Integer placeId){
        return true;
    }
}
