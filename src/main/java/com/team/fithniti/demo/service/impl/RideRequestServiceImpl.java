package com.team.fithniti.demo.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.team.fithniti.demo.dto.RideRequestFilterOption;
import com.team.fithniti.demo.dto.response.RideRequestDTO;
import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.QRideRequest;
import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.model.RideRequest;
import com.team.fithniti.demo.repository.PassengerRepo;
import com.team.fithniti.demo.repository.RideRequestRepo;
import com.team.fithniti.demo.service.RideRequestService;
import com.team.fithniti.demo.service.RideService;
import com.team.fithniti.demo.util.Order;
import com.team.fithniti.demo.util.RideRequestState;
import com.team.fithniti.demo.util.RideState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RideRequestServiceImpl implements RideRequestService {
    @Autowired
    private RideRequestRepo rideRequestRepo;

    @Autowired
    private RideService rideService;

    @Autowired
    private PassengerRepo passengerRepo; // <-- to Delete

    @Override
    public RideRequestDTO create(Long passengerId, Long rideId) {
        // check AuthUser access
        Passenger passenger = new Passenger();//Todo: replace with passengerService.findById(id)
        Ride ride = rideService.findEntityById(rideId);
        if ( rideRequestRepo.existsByPassengerAndRide(passenger,ride)){
            throw new InvalidResource("RideRequest already exists","Failed to submit request");
        }
        RideRequest rideRequest = RideRequest.builder()
                .ride(ride)
                .passenger(passenger)
                .state(RideRequestState.PENDING)
                .build();
        return RideRequestDTO.fromEntity(rideRequestRepo.save(rideRequest));
        // TODO: notification to driver
    }

    @Override
    public void cancel(Long passengerId, Long rideId) {
        Optional<Passenger> passenger = passengerRepo.findById(passengerId); // todo: use passengerServiceFindById(passengerId)
        assert passenger.isPresent();
        // check user access
        Ride ride = rideService.findEntityById(rideId);
        if ( ! rideRequestRepo.existsByPassengerAndRide(passenger.get(),ride)){
            throw new ResourceNotFound("No RideRequest found ","Failed to cancel request");
        }
        if ( ! ride.getRideState().equals(RideState.PENDING) ){
            throw new InvalidResource("Ride is already DONE or IN_PROGRESS","Failed to cancel request");
        }
        rideRequestRepo.delete(rideRequestRepo.findByPassengerAndRide(passenger.get(),ride));
        // push notif --> update available places;
    }

    @Override
    public void handle(Long reqId, RideRequestState state) {
        RideRequest rideRequest = rideRequestRepo.findById(reqId).orElseThrow(
                () -> new ResourceNotFound("RIDE_REQUEST_FAILED", "No request found"));
//        checkUserAccess
        rideRequest.setState(state);
        rideRequestRepo.save(rideRequest);
        // TODO: Generate notification to passenger
        if (state.equals(RideRequestState.ACCEPTED)){
            HashMap<String, Object> updates = new HashMap<>();
            updates.put("availablePlaces", (byte) 1);
            rideService.update(rideRequest.getRide().getId(),updates);
        }
    }

    @Override
    public Page<RideRequestDTO> getRideRequests(Long rideId, RideRequestFilterOption options) {
        Ride ride = rideService.findEntityById(rideId);
        // check AuthUser access : only Ride Driver can see
        QRideRequest qRideRequest = QRideRequest.rideRequest;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qRideRequest.ride.id.eq(rideId));
        return doYourJob(builder,qRideRequest,options);
    }


    @Override
    public Page<RideRequestDTO> getPassengerRequests(Long passengerId, RideRequestFilterOption options) {
        Optional<Passenger> passenger = passengerRepo.findById(passengerId); // todo:  passengerServiceFindById(passengerId)
        assert passenger.isPresent();
        BooleanBuilder builder = new BooleanBuilder();
        QRideRequest qRideRequest = QRideRequest.rideRequest;
        builder.and(qRideRequest.passenger.id.eq(passengerId));
        return doYourJob(builder,qRideRequest,options);
    }

    private Page<RideRequestDTO> doYourJob(BooleanBuilder builder, QRideRequest qRideRequest, RideRequestFilterOption options) {
        if ( options.getMinPassengerRating() != null )
            builder.and(qRideRequest.passenger.rating.goe(options.getMinPassengerRating()));
        if ( options.getRequestState() != null )
            builder.and(qRideRequest.state.eq(options.getRequestState()));
        if (options.getStartDate() != null ){
            LocalDateTime day_1 = options.getStartDate().atTime(LocalTime.now()).minusDays(1);
            LocalDateTime day_2 = options.getStartDate().atTime(LocalTime.now()).plusDays(1);
            builder.and(qRideRequest.createdDate.between(day_1,day_2));
        }
        if (options.getStartTime()!= null){
            builder.and(qRideRequest.ride.startTime.after(options.getStartTime()));
        }
        Class<RideRequest> c = RideRequest.class;
        List<String> fields = Arrays.stream(c.getDeclaredFields()).map(field -> field.getName()).collect(Collectors.toList());
        List<String> validProperties = Arrays.stream(options.getProperties()).filter(s -> fields.contains(s)).collect(Collectors.toList());
        Sort.Direction sort = ( options.getOrder().equals(Order.ASC)) ? Sort.Direction.ASC : Sort.Direction.DESC ;

        PageRequest pageRequest = PageRequest.of(options.getPage(), options.getLimit(), sort, validProperties.toArray(String[]::new));
        return ( builder.getValue() == null ) ? rideRequestRepo.findAll(pageRequest).map(RideRequestDTO::fromEntity)
                :rideRequestRepo.findAll(builder,pageRequest).map(RideRequestDTO::fromEntity);
    }
}