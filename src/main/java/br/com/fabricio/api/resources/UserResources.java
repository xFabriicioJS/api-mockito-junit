package br.com.fabricio.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabricio.api.domain.User;
import br.com.fabricio.api.services.UserService;

@RestController
@RequestMapping(value ="/user")
public class UserResources {
    

    @Autowired
    UserService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(service.findById(id));
    }

}
