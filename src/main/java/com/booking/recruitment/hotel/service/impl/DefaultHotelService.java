package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.exception.ElementNotFoundException;
import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.CityRepository;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class DefaultHotelService implements HotelService {
  private final HotelRepository hotelRepository;
  private final CityRepository cityRepository;

  @Autowired
  DefaultHotelService(HotelRepository hotelRepository, CityRepository cityRepository) {
    this.hotelRepository = hotelRepository;
    this.cityRepository = cityRepository;
  }

  @Override
  public List<Hotel> getAllHotels() {
    return hotelRepository.findAll();
  }

  @Override
  public Optional<Hotel> getHotelById(Long id) {
    return hotelRepository.findById(id);
  }

  @Override
  public List<Hotel> getHotelsByCity(Long cityId) {
    return hotelRepository.findAll().stream()
        .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
        .collect(Collectors.toList());
  }

  @Override
  public Hotel createNewHotel(Hotel hotel) {
    if (hotel.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Hotel");
    }

    return hotelRepository.save(hotel);
  }

  @Override
  public Hotel deleteHotelById(Long id) {
    Optional<Hotel> hotelOptional = hotelRepository.findById(id);
    if (hotelOptional.isPresent()) {
      Hotel deletedHotel = hotelOptional.get();
      deletedHotel.setDeleted(true);
      return hotelRepository.save(deletedHotel);
    }
    throw new ElementNotFoundException("Unable to delete hotel, invalid hotel id.");
  }

  @Override
  public List<Hotel> findHotelsClosestToCityCenter(Long cityId) {
    Optional<City> maybeCity = cityRepository.findById(cityId);
    if(!maybeCity.isPresent()) {
      throw new ElementNotFoundException("Invalid City Id.");
    }
    City city = maybeCity.get();
    return hotelRepository.findHotelsClosestToCityCenter(
            city.getCityCentreLatitude(),
            city.getCityCentreLongitude(),
            cityId, PageRequest.of(0, 3));
  }
}
