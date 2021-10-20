package com.example.nauchki.controller;

import com.example.nauchki.model.dto.UserDto;
import com.example.nauchki.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Пока не актуально.
 */
@RestController
@RequestMapping("/number")
public class SmsController {

    private SmsService serviceSms;

    public SmsController(SmsService serviceSms) {
        this.serviceSms = serviceSms;
    }

    /*@PostMapping()
    public ResponseEntity<HttpStatus> addNumber(@RequestBody UserDto userDto) {
        return serviceSms.sendSms(userDto.getNumber(),"Test","TEST-SMS") ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
*/
}
