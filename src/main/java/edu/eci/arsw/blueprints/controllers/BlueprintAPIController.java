/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;

/**
 *
 * @author hcadavid
 */

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    @Qualifier("Blueprint")
    private BlueprintsServices bps;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllBluepr(){
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(bps.getAllBlueprints(),HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error getAllBluepr",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{author}",method = RequestMethod.GET)
    public ResponseEntity<?> getAuthorBluepr(@PathVariable String author){
        try {
            return new ResponseEntity<>(bps.getBlueprintsByAuthor(author), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException  ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error getAuthorBluepr",HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/{author}/{bpname}",method = RequestMethod.GET)
    public ResponseEntity<?> getBluepr(@PathVariable String author,@PathVariable String bpname){
        try {
            return new ResponseEntity<>(bps.getBlueprint(author,bpname), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException   ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error getBluepr",HttpStatus.NOT_FOUND);
        }

    }
    @RequestMapping(value = "/createBlueprint",method = RequestMethod.POST)
    public ResponseEntity<?> createBlueprint(@RequestBody Blueprint bl){
        try {
            bps.addNewBlueprint(bl);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error createBlueprint",HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{author}/{bpname}",method = RequestMethod.PUT)
    public ResponseEntity<?> putBlueprint(@PathVariable String author,@PathVariable String bpname,@RequestBody Blueprint bp){
        try {
            bps.setModif(author,bpname,bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
