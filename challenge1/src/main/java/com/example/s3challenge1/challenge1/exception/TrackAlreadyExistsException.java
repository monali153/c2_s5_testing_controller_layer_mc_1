package com.example.s3challenge1.challenge1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Track already exists")
public class TrackAlreadyExistsException extends Exception{
}
