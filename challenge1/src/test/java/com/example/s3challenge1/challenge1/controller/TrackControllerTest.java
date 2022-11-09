package com.example.s3challenge1.challenge1.controller;

import com.example.s3challenge1.challenge1.domain.Artist;
import com.example.s3challenge1.challenge1.domain.Track;
import com.example.s3challenge1.challenge1.exception.TrackAlreadyExistsException;
import com.example.s3challenge1.challenge1.service.TrackServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TrackServiceImpl trackService;

    @InjectMocks
    private TrackController trackController;

    Track track;
    Artist artist;

    @BeforeEach
    public void setUp(){
        artist = new Artist(41,"Neeti Mohan");
        track = new Track(11,"track8",4,artist);
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
    }

    @AfterEach
    public void tearDown(){
        artist = null;
        track = null;
    }

    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }



    @Test
    public void addCustomer() throws Exception {
        when(trackService.addTrack(any())).thenReturn(track);
        mockMvc.perform(
                post("/trackdata/api/track").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(track))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).addTrack(any());
    }

    @Test
    public void addCustomerTestFailure() throws Exception {
        when(trackService.addTrack(any())).thenThrow(TrackAlreadyExistsException.class);
        mockMvc.perform(
                post("/trackdata/api/track").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(track))).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).addTrack(any());
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        when(trackService.deleteTrackById(anyInt())).thenReturn(true);
        mockMvc.perform(
                        delete("/trackdata/api/track/11")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonToString(track)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).deleteTrackById(anyInt());
    }
}
