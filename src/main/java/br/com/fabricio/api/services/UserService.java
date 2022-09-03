package br.com.fabricio.api.services;

import br.com.fabricio.api.domain.User;

public interface UserService {
    
    User findById(Integer id);

}
