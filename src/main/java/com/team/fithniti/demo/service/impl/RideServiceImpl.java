package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.dto.RideFilterOption;
import com.team.fithniti.demo.dto.request.NewRide;
import com.team.fithniti.demo.dto.response.RideDTO;
import com.team.fithniti.demo.dto.response.TagDto;
import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.Driver;
import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.repository.RideRepo;
import com.team.fithniti.demo.service.RideService;
import com.team.fithniti.demo.util.Order;
import com.team.fithniti.demo.validator.RideValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class RideServiceImpl implements RideService{
    @Autowired
    private RideRepo rideRepo;


    @Override
    public RideDTO create(NewRide ride) {
        if ( ride == null )
            throw new InvalidResource(null,"INVALID_RIDE","Can't persist null entity");
        // driverService.findById(ride.GetIdDriver)

        Driver driver = new Driver(); // == driverService.findById(ride.getDriverId)
        AppUser user = driver.getUser();
        // checkUserAccess(user) ; //
//        Example
//        private void checkUserAccess(AppUser user){
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            AppUser authenticatedUser = userDao.findByLogin(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("Oops!, Something went wrong"));
//            if ( ! user.equals(authenticatedUser) && !authenticatedUser.isAdmin() ) {
//                throw new AccessDeniedException("Oops! You don't have permissions!");
//            }
//        }

        // end checkUser

//        CarService.findByBrand(ride.getCar().getBrand(),ride.getCar().getCarModel());

        return RideDTO.fromEntity(
                rideRepo.save( Ride.builder()
                        .description(ride.getDescription())
                        .startTime(ride.getStartTime())
                        .maxPlaces(ride.getMaxPlaces())
                        .pathway(ride.getPathway())
                        .price(ride.getPrice())
                        .rideType(ride.getRideType())
                        .tags(ride.getTags())
                        .driver(driver)
                        .build()
                )
        );
    }

    @Override
    public RideDTO findById(Long id) {
        return RideDTO.fromEntity(rideRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("No Ride found")));
    }

    @Override
    public Page<RideDTO> findAll(RideFilterOption options) {
        // TODO: 11/4/21 impl the rest
        Sort.Direction sort = ( options.getOrder().equals(Order.ASC)) ? Sort.Direction.ASC : Sort.Direction.DESC ;
        PageRequest pageRequest = PageRequest.of(options.getPage(), options.getLimit(), sort,options.getProperties());
        return  rideRepo.findAll(pageRequest).map(RideDTO::fromEntity);
    }

    @Override
    public Page<RideDTO> findDriverRides(Long driverId, RideFilterOption options) {
        // TODO: 11/4/21 impl the rest + Q_DSL
        return null;
    }

    @Override
    public void deleteById(Long rideId) {
        rideRepo.deleteById(findById(rideId).getId());
    }

    @Override
    public void update(Long rideId, Long driverId, Map<String, Object> changes) {
        // TODO: 11/4/21 impl
    }
}
