package net.zoltancsaszi.actionmonitor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;

/**
 * Rest controller component to provide information about the application.
 *
 * @author Zoltan Csaszi
 */
@RestController
@RequestMapping("/api")
public class ApplicationInfoController {

    @GetMapping("/availability")
    public ResponseEntity<String> getAvailability() {
        String body = Json.createObjectBuilder()
                .add("message", "OK")
                .build()
                .toString();

        return new ResponseEntity(body, HttpStatus.OK);
    }

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        String body = Json.createObjectBuilder()
                .add("version", this.getClass().getPackage().getImplementationVersion())
                .build()
                .toString();

        return new ResponseEntity(body, HttpStatus.OK);
    }
}
