package com.example.s3challenge1.challenge1.service;

import com.example.s3challenge1.challenge1.domain.Artist;
import com.example.s3challenge1.challenge1.domain.Track;
import com.example.s3challenge1.challenge1.exception.TrackAlreadyExistsException;
import com.example.s3challenge1.challenge1.exception.TrackNotFoundException;
import com.example.s3challenge1.challenge1.repository.TrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrackServiceTest {

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private TrackServiceImpl trackService;

    Artist artist1, artist2;
    Track track1, track2;
    List<Track> trackList;

    @BeforeEach
    public void setUp(){
        artist1 = new Artist(40,"Meet Bro's");
        track1 = new Track(7,"track7",5,artist1);
        trackList = Arrays.asList(track1);
    }

    @AfterEach
    public void tearDown(){
        track1 = null;
    }

    @Test
    public void addTrackTest() throws TrackAlreadyExistsException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(null));
        when(trackRepository.save(any())).thenReturn(track1);
        assertEquals(track1,trackService.addTrack(track1));
        verify(trackRepository, times(1)).save(any());
        verify(trackRepository,times(1)).findById(any());
    }

    @Test
    public void addTrackTestFailure(){
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        assertThrows(TrackAlreadyExistsException.class,()->trackService.addTrack(track1));
        verify(trackRepository,times(0)).save(any());
        verify(trackRepository,times(1)).findById(any());
    }

    @Test
    public void deleteTrackByIdTest() throws TrackNotFoundException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        boolean flag = trackService.deleteTrackById(track1.getTrackId());
        assertEquals(true,flag);
        verify(trackRepository,times(1)).deleteById(any());
        verify(trackRepository,times(1)).findById(any());
    }

    @Test
    public void deleteTrackByIdTestFailure(){
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        assertThrows(TrackNotFoundException.class,()->trackService.deleteTrackById(track1.getTrackId()));
        verify(trackRepository,times(0)).deleteById(any());
        verify(trackRepository,times(1)).findById(any());
    }
}
