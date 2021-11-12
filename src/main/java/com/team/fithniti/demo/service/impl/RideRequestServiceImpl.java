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
import java.util.HashMap;
import java.util.Optional;

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
    public Object create(Long passengerId, Long rideId) {
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
        return rideRequestRepo.save(rideRequest);
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
        rideRequest.setState(state);
        // TODO: Generate notification to passenger
        rideRequestRepo.save(rideRequest);
        if (state.equals(RideRequestState.ACCEPTED)){
            HashMap<String, Byte> updates = new HashMap<>();
            updates.put("Available Places", null);
//            rideService.update(rideRequest.getRide().getId(),null,updates);
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

        if ( options.getMinPassengerRating() != null ) builder.and(qRideRequest.passenger.rating.goe(options.getMinPassengerRating()));
        if ( options.getRequestState() != null ) builder.and(qRideRequest.state.eq(options.getRequestState()));
        // TODO: complete filter options if ?
        // TODO: validate options.getProperties()
        Sort.Direction sort = ( options.getOrder().equals(Order.ASC)) ? Sort.Direction.ASC : Sort.Direction.DESC ;
        PageRequest pageRequest = PageRequest.of(options.getPage(), options.getLimit(), sort, options.getProperties());

        return ( builder.getValue() == null ) ? rideRequestRepo.findAll(pageRequest).map(RideRequestDTO::fromEntity)
                :rideRequestRepo.findAll(builder,pageRequest).map(RideRequestDTO::fromEntity);
    }
}