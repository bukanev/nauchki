package com.example.nauchki.controller;

import com.example.nauchki.service.SmsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


/**
 * Пока не актуально.
 */
@ApiIgnore
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
