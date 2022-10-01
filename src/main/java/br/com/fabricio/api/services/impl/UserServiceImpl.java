package br.com.fabricio.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabricio.api.domain.User;
import br.com.fabricio.api.domain.dto.UserDTO;
import br.com.fabricio.api.repositories.UserRepository;
import br.com.fabricio.api.services.UserService;
import br.com.fabricio.api.services.exceptions.DataIntegratyViolationException;
import br.com.fabricio.api.services.exceptions.ObjectNotFoundException;


@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    UserRepository repository;

    @Autowired
    ModelMapper mapper;


    @Override
    public User findById(Integer id){
    	User obj = repository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Usuario nao encontrado. Id = " + id));
    	
    	return obj;
    	
    }
    
    public User create(UserDTO obj) {
    	findByEmail(obj);
    	return repository.save(mapper.map(obj, User.class));
    }

    
    public List<User> findAll() {
    	return repository.findAll();
    }
    
    public void findByEmail(UserDTO obj) {
    	Optional<User> user = repository.findByEmail(obj.getEmail());
    
    	if(user.isPresent()) {
    		throw new DataIntegratyViolationException("Email já cadastrado no sistema! Por favor insira um outro email.");
    	}
    }
    

}
