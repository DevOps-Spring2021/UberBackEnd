package com.api.uber.controller;

import com.api.uber.model.Ride;
import com.api.uber.services.RideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;


@Controller
@RestController
@RequestMapping("/v1/rides/**")
public class RideController {

    @Autowired
    RideService rideService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value="/book", method = RequestMethod.POST,  produces = "application/json")
    public ResponseEntity<Object> bookRide(@RequestBody Ride ride) {

        try{
            rideService.createRide(ride);
            log.info("ride booked, rideID:" + ride.getRideID());
            return new ResponseEntity<>(ride, HttpStatus.CREATED);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET,  produces = "application/json")
    public  ResponseEntity<Object> getRide( @PathVariable("id") Long id) {
        try {
            Ride ride = rideService.getRideByID(id);
            if(ride!= null) {
                log.info("get ride, rideID:"+ride.getRideID());
                return new ResponseEntity<>(ride, HttpStatus.OK);
            } else {
                log.info("ride not found, rideID:"+id);
                return new ResponseEntity<>("Ride not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/complete/{id}", method = RequestMethod.POST,  produces = "application/json")
    public  ResponseEntity<Object> rideCompleted( @PathVariable("id") Long id) {
        try {
            Ride ride = rideService.getRideByID(id);
            if(ride!= null) {
                ride.setComplete(true);
                rideService.saveRide(ride);
                log.info("ride completed, rideID:"+ id);
                return new ResponseEntity<>("Ride Completed", HttpStatus.OK);
            } else {
                log.info("ride not found, rideID:"+id);
                return new ResponseEntity<>("Ride not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/feedback/{id}", method = RequestMethod.POST,  produces = "application/json")
    public  ResponseEntity<Object> rideFeedBack(@RequestBody String feedback,@PathVariable("id") Long id) {
        try {
            Ride ride = rideService.getRideByID(id);
            if(ride!= null && ride.isComplete()) {
                ride.setFeedback(feedback);
                rideService.saveRide(ride);
                log.info("ride feedback set, rideID:"+ id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.info("ride not found, rideID:"+id);
                return new ResponseEntity<>("Ride not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/cancel/{id}", method = RequestMethod.POST,  produces = "application/json")
    public  ResponseEntity<Object> rideCancel(@PathVariable("id") Long id) {
        try {
            Ride ride = rideService.getRideByID(id);
            if(ride!= null && !ride.isComplete()) {
                ride.setCancel(true);
                rideService.saveRide(ride);
                log.info("ride canceled, rideID:"+ id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.info("ride not found, rideID:"+id);
                return new ResponseEntity<>("Ride not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/all", method = RequestMethod.GET,  produces = "application/json")
    public  ResponseEntity<Object> getAllRides() {
        try {
            //user id
            LinkedHashSet<Ride> list = rideService.getUserRides(id);
            if(list != null) {
                log.info("all rides of userID:"+ id);
                return new ResponseEntity<>(list,HttpStatus.OK);
            } else {
                log.info("user not found, userID:"+id);
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
