package br.com.fabricio.api.services;

import java.util.List;

import br.com.fabricio.api.domain.User;

public interface UserService {
    
    User findById(Integer id);
    
    List<User> findAll();


}
