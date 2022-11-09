package com.example.s3challenge1.challenge1.controller;

import com.example.s3challenge1.challenge1.domain.Track;
import com.example.s3challenge1.challenge1.exception.TrackAlreadyExistsException;
import com.example.s3challenge1.challenge1.exception.TrackNotFoundException;
import com.example.s3challenge1.challenge1.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trackdata/api/")
public class TrackController {

    private TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService){
        this.trackService = trackService;
    }

    @PostMapping("/track")
    public ResponseEntity<?> insertTrack(@RequestBody Track track) throws TrackAlreadyExistsException {
        ResponseEntity responseEntity;
        try {
            trackService.addTrack(track);
            responseEntity = new ResponseEntity("Succesfully Added 1 track",HttpStatus.CREATED);
        }catch (TrackAlreadyExistsException ex){
            throw  new TrackAlreadyExistsException();
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/tracks")
    public ResponseEntity<?> getTrack(){

        ResponseEntity responseEntity;
        try {
            responseEntity = new ResponseEntity<>(trackService.getAllTrack(),HttpStatus.FOUND);
        }catch (Exception e){
            responseEntity = new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/track/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable int trackId) throws TrackNotFoundException {

        ResponseEntity responseEntity;
        try {
            trackService.deleteTrackById(trackId);
            responseEntity = new ResponseEntity("Successfully deleted track.",HttpStatus.OK);
        }catch (TrackNotFoundException tex){
            throw new TrackNotFoundException();
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/track/{trackRating}")
    public ResponseEntity<?> getTrackForRating4(@PathVariable int trackRating) throws TrackNotFoundException {
        ResponseEntity responseEntity;
        try {
            responseEntity = new ResponseEntity<>(trackService.getTrackGreaterThan4(trackRating),HttpStatus.OK);
        }catch (TrackNotFoundException tex){
            throw new TrackNotFoundException();
        }
        catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/artist/{artistName}")
    public ResponseEntity<?> getTrackByArtist(@PathVariable String artistName) throws TrackNotFoundException {
        ResponseEntity responseEntity;
        try {
            responseEntity = new ResponseEntity<>(trackService.getTrackByArtistName(artistName),HttpStatus.OK);
        }catch (TrackNotFoundException tex){
            throw new TrackNotFoundException();
        }
        catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
