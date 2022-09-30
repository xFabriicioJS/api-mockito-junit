package br.com.fabricio.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabricio.api.domain.User;
import br.com.fabricio.api.repositories.UserRepository;
import br.com.fabricio.api.services.UserService;


@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    UserRepository repository;



    @Override
    public User findById(Integer id){
        Optional<User> obj = repository.findById(id);

        return obj.orElse(null);
    }

    
    public List<User> findAll() {
    	return repository.findAll();
    }
}
