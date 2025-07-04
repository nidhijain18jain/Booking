package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final HotelService hotelService;

    @Autowired
    public SearchController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> searchHotelsByCity(
            @PathVariable Long cityId,
            @RequestParam String sortBy) {
        switch (sortBy) {
            case "distance":
                return hotelService.findHotelsClosestToCityCenter(cityId);
            default:
                return hotelService.getAllHotels();
        }

    }
}
