package com.example.nauchki.controller;

import com.example.nauchki.model.Children;
import com.example.nauchki.model.dto.ChildrenDto;
import com.example.nauchki.service.ChildrenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChildrenController {
    private final ChildrenService childrenService;

    @PostMapping("/children/{id}")
    public ResponseEntity<ResponseStatus> addChildren(@PathVariable Long id,ChildrenDto childrenDto) {
        return childrenService.addChildren(id, childrenDto) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/getchildren/{id}")
    public List<ChildrenDto> getChildren(@PathVariable Long id) {
        return childrenService.getChildren(id);
    }

    @PostMapping("/getchildren")
    public List<ChildrenDto> getChildrenList(Children children) {
        return childrenService.getChildren(children);
    }

    @PostMapping("/children")
    public ResponseEntity<ResponseStatus> editChildren( Children children) {
        return childrenService.editChildren(children) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/deletechildren")
    public ResponseEntity<ResponseStatus> deleteChildren(Children children) {
        return childrenService.deleteChildren(children) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
