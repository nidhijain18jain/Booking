package com.booking.recruitment.hotel.service;

import com.booking.recruitment.hotel.model.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {
  List<Hotel> getAllHotels();

  Optional<Hotel> getHotelById(Long id);

  List<Hotel> getHotelsByCity(Long cityId);

  Hotel createNewHotel(Hotel hotel);

  Hotel deleteHotelById(Long Id);

  List<Hotel> findHotelsClosestToCityCenter(Long cityId);
}
