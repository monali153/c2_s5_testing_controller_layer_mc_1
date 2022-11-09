package com.example.s3challenge1.challenge1.repository;

import com.example.s3challenge1.challenge1.domain.Artist;
import com.example.s3challenge1.challenge1.domain.Track;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class TrackRepositoryTest {

    @Autowired
    private TrackRepository trackRepository;

    private Track track;
    private Artist artist;

    @BeforeEach
    public void setUp(){
        artist = new Artist(20,"Sonu Nigam");
        track = new Track(6,"track6",4,artist);
    }

    @AfterEach
    public void tearDown(){
        artist = null;
        track = null;
        trackRepository = null;
    }

    @Test
    @DisplayName("Test case for saving track object")
    public void saveTrackTest(){
        trackRepository.save(track);
        Track track1 = trackRepository.findById(track.getTrackId()).get();
        assertNotNull(track1);
        assertEquals(track.getTrackId(),track1.getTrackId());
    }

    @Test
    @DisplayName("Test case for retrieving track object")
    public void fetchTrackTest(){
        trackRepository.insert(track);
        List<Track> trackList = trackRepository.findAll();
        assertEquals(6,trackList.size());
        assertEquals("Justin Bieber",trackList.get(0).getTrackArtist().getArtistName());
    }

    @Test
    @DisplayName("Test case for deleting track object")
    public void deleteTrackTest(){
        Track track1 = trackRepository.findById(track.getTrackId()).get();
        trackRepository.delete(track1);
        assertEquals(Optional.empty(),trackRepository.findById(track1.getTrackId()));
    }

}
