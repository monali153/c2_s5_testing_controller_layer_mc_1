package com.example.s3challenge1.challenge1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Track is not found")
public class TrackNotFoundException extends Exception{
}
