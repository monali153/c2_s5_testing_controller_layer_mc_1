package com.example.s3challenge1.challenge1.repository;

import com.example.s3challenge1.challenge1.domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends MongoRepository<Track, Integer> {

    @Query("{ 'trackRating' : {$gt : 4 }}")
    List<Track> findByTrackRating(int trackRating);

    @Query("{ 'trackArtist.artistName' : {$in : [?0] }}")
    List<Track> findAllTrackByArtistName(String artistName);
}
