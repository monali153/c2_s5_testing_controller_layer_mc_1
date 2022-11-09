package com.example.s3challenge1.challenge1.service;

import com.example.s3challenge1.challenge1.domain.Track;
import com.example.s3challenge1.challenge1.exception.TrackAlreadyExistsException;
import com.example.s3challenge1.challenge1.exception.TrackNotFoundException;

import java.util.List;

public interface TrackService {

    Track addTrack(Track track) throws TrackAlreadyExistsException;
    List<Track> getAllTrack() throws Exception;

    boolean deleteTrackById(int trackId) throws TrackNotFoundException;
    List<Track> getTrackGreaterThan4(int trackRating) throws TrackNotFoundException;

    List<Track> getTrackByArtistName(String artistName) throws TrackNotFoundException;
}
