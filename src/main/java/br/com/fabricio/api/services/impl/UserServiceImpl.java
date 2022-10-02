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
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    public User findById(Integer id) {
        User obj = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario nao encontrado. Id = " + id));

        return obj;

    }

    @Override
    public User create(UserDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User update(UserDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    public void findByEmail(UserDTO obj) {
        Optional<User> user = repository.findByEmail(obj.getEmail());

        if (user.isPresent() && !user.get().getId().equals(obj.getId())) {
            throw new DataIntegratyViolationException(
                    "Email já está cadastrado no sistema! Por favor insira um outro email.");
        }
    }

    @Override
    public void delete(Integer userId) {
        // verificando se o id que foi recebido realmente existe no banco de dados,
        // poderiamos utilizar também o metodo findById acima.

        User user = repository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário nao existe, ID = " + userId));

        if (user != null) {
            repository.deleteById(userId);
        }

    }

}
