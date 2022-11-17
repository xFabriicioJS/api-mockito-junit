package br.com.fabricio.api.services.impl;

import br.com.fabricio.api.domain.User;
import br.com.fabricio.api.domain.dto.UserDTO;
import br.com.fabricio.api.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Fabricio";
    public static final String EMAIL = "fabricio@hotmail.com";
    public static final String PASSWORD = "123";

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;


    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }


    //Testando o método findById, deve retornar uma instancia de User
    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        //Mockando uma resposta para quando o método findById do repository for chamado
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        //Verificando se a minha resposta não é nula
        Assertions.assertNotNull(response);

        //"Assertando" que a minha resposta que veio lá do meu service realmente pertence a classe Usuário (User)
        Assertions.assertEquals(User.class, response.getClass());

        //Verificando se o id do meu objeto é realmente o id que passei como parâmetro
        Assertions.assertEquals(ID, response.getId());

        //Outras verificações
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());

    }

    @Test
    void create() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void delete() {
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}