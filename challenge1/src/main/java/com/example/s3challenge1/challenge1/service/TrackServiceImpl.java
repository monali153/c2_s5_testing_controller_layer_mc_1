package com.example.s3challenge1.challenge1.service;

import com.example.s3challenge1.challenge1.domain.Track;
import com.example.s3challenge1.challenge1.exception.TrackAlreadyExistsException;
import com.example.s3challenge1.challenge1.exception.TrackNotFoundException;
import com.example.s3challenge1.challenge1.repository.TrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService{

    private TrackRepository trackRepository;

    public TrackServiceImpl(TrackRepository trackRepository){
        this.trackRepository = trackRepository;
    }
    @Override
    public Track addTrack(Track track) throws TrackAlreadyExistsException {
        if(trackRepository.findById(track.getTrackId()).isPresent()){
            throw new TrackAlreadyExistsException();
        }
        return trackRepository.save(track);
    }

    @Override
    public List<Track> getAllTrack() {
        return trackRepository.findAll();
    }

    @Override
    public boolean deleteTrackById(int trackId) throws TrackNotFoundException {
        boolean result = false;

        if(trackRepository.findById(trackId).isPresent()){
            throw new TrackNotFoundException();
        }else {
            trackRepository.deleteById(trackId);
            result = true;
        }
        return result;
    }

    @Override
    public List<Track> getTrackGreaterThan4(int trackRating) {
        return trackRepository.findByTrackRating(trackRating);
    }

    @Override
    public List<Track> getTrackByArtistName(String artistName) {
        return trackRepository.findAllTrackByArtistName(artistName);
    }
}
