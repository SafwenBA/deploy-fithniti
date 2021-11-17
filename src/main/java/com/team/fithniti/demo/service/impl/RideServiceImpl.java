package com.team.fithniti.demo.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.team.fithniti.demo.dto.RideFilterOption;
import com.team.fithniti.demo.dto.request.NewRide;
import com.team.fithniti.demo.dto.response.RideDTO;
import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.*;
import com.team.fithniti.demo.repository.RideRepo;
import com.team.fithniti.demo.service.CarModelService;
import com.team.fithniti.demo.service.RideService;
import com.team.fithniti.demo.util.Order;
import com.team.fithniti.demo.util.RideState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class RideServiceImpl implements RideService{
    @Autowired
    private RideRepo rideRepo;

    @Autowired
    private CarModelService carModelService;

    @Override
    public RideDTO create(NewRide ride) {
        if ( ride == null )
            throw new InvalidResource(null,"INVALID_RIDE","Can't persist null entity");
        if ( ! carModelService.existsCarModelByCarBrandAndModel(ride.getCar().getBrand(),ride.getCar().getCarModel())) {
            throw new InvalidResource("Failed to create Ride, Invalid Car");
        }
        Driver driver = new Driver(); // driverService.findById(ride.GetIdDriver) + check access
        return RideDTO.fromEntity(
                rideRepo.save( Ride.builder()
                        .description(ride.getDescription())
                        .startTime(ride.getStartTime())
                        .maxPlaces(ride.getMaxPlaces())
                        .availablePlaces(ride.getMaxPlaces())
                        .pathway(ride.getPathway())
                        .price(ride.getPrice())
                        .rideType(ride.getRideType())
                        .tags(ride.getTags())
                        .driver(driver)
                        .build()
        ));
    }

    @Override
    public RideDTO findById(Long id) {
        return RideDTO.fromEntity(rideRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("INVALID_RIDE","No Ride found")));
    }

    @Override
    public Ride findEntityById(Long id) {
        return rideRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("INVALID_RIDE","No Ride found"));
    }

    @Override
    public Page<RideDTO> findAll(RideFilterOption options) {
        BooleanBuilder builder = new BooleanBuilder();
        final QRide qRide = QRide.ride;
        return doYourJob(qRide,builder,options);
    }

    @Override
    public Page<RideDTO> findDriverRides(Long driverId, RideFilterOption options) {
        BooleanBuilder builder = new BooleanBuilder();
        final QRide qRide = QRide.ride;
        builder.and(qRide.driver.id.eq(driverId));
        return doYourJob(qRide, builder, options);
    }

    private Page<RideDTO> doYourJob(QRide qRide, BooleanBuilder builder, RideFilterOption options) {
        if ( options == null ){
            builder.and(qRide.startTime.after(LocalTime.now().plusMinutes(15))).and(qRide.rideState.eq(RideState.PENDING));
        }
        else {
            if ( options.getMaxPlaces() != null ) builder.and(qRide.maxPlaces.eq(options.getMaxPlaces()));
            if ( options.getAvailablePlaces() != null ) builder.and(qRide.availablePlaces.eq(options.getAvailablePlaces()));
            if ( options.getCarBrand() != null ) builder.and(qRide.car.brand.equalsIgnoreCase(options.getCarBrand()));
            if ( options.getCarModel() != null ) builder.and(qRide.carModel.model.equalsIgnoreCase(options.getCarModel()));
            if ( options.getRideState() != null ) builder.and(qRide.rideState.eq(options.getRideState()));
            if ( options.getRideType() != null ) builder.and(qRide.rideType.eq(options.getRideType()));
            if ( options.getMaxPrice() > options.getMinPrice() ) builder.and(qRide.price.between(options.getMinPrice(), options.getMaxPrice()));

            if ( options.getMaxDriverRating() > options.getMinDriverRating() ) builder.and(qRide.driver.rating.between(options.getMaxDriverRating() , options.getMinDriverRating()));
            if ( options.getPathway() != null ) builder.and(qRide.pathway.eq(options.getPathway()));

            if ( options.getStartDate() != null ) {
                LocalDateTime day_1 = options.getStartDate().atTime(LocalTime.now()).minusDays(1);
                LocalDateTime day_2 = options.getStartDate().atTime(LocalTime.now()).plusDays(1);
                builder.and(qRide.createdDate.between(day_1,day_2));
            }
            if ( options.getStartTimeTo().isAfter(options.getStartTimeFrom()) ) builder.and(qRide.startTime.between(options.getStartTimeFrom(), options.getStartTimeTo()));
            if ( options.getTags() != null && !options.getTags().isEmpty()) builder.and(qRide.maxPlaces.eq(options.getMaxPlaces()));
        }
        Class<Ride> c = Ride.class;
        List<String> fields = Arrays.stream(c.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        List<String> validProperties = Arrays.stream(options.getProperties()).filter(s -> fields.contains(s)).collect(Collectors.toList());
        Sort.Direction sort = ( options.getOrder().equals(Order.ASC)) ? Sort.Direction.ASC : Sort.Direction.DESC ;
        PageRequest pageRequest = PageRequest.of(options.getPage(), options.getLimit(), sort,validProperties.toArray(String[]::new));
        return (builder.getValue() != null ) ? rideRepo.findAll(builder,pageRequest).map(RideDTO::fromEntity) : rideRepo.findAll(pageRequest).map(RideDTO::fromEntity);
    }

    @Override
    public void deleteById(Long rideId) {
        rideRepo.deleteById(findById(rideId).getId());
    }

    @Override
    public void update(Long rideId, Map<String, Object> changes) {
        try {
            final Ride ride = findEntityById(rideId);
            changes.forEach((k, v) -> {
                switch (k){
                    case "RideState": {
                        ride.setRideState((RideState) v);
                        // push notif
                        break;
                    }
                    case "startTime": {
                        assert ((LocalTime) v).isAfter(LocalTime.now().plusMinutes(15));
//                        assert driver.id = authUser.id or is admin, or auto update as  +-availablePlaces
//                        assert ride.state = pending --> doJob()
                        ride.setStartTime((LocalTime) v);
                        // push notif
                        break;
                    }
                    case "maxPlaces": {
                        ride.setMaxPlaces((Byte) v);
                        //...
                        break;
                    }
                    case "availablePlaces": {
                        Byte availablePlaces = ride.getAvailablePlaces();
                        if (availablePlaces == ride.getMaxPlaces()){
                            throw new InvalidResource("Max limit exceeded","Failed to update availablePlaces");
                        }
                        availablePlaces++;
                        ride.setAvailablePlaces(availablePlaces);
                        rideRepo.save(ride);
                        break;
                    }
                    // TODO: carry on
                }
            });
        }catch (Exception e){
            throw new  InvalidResource("FAILED_TO_UPDATE_RIDE",e.getMessage());
        }
    }
}
