package br.com.fabricio.api.resources;

import br.com.fabricio.api.domain.User;
import br.com.fabricio.api.domain.dto.UserDTO;
import br.com.fabricio.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserResourcesTest {


    public static final Integer ID = 1;
    public static final String NAME = "Fabricio";
    public static final String EMAIL = "fabricio@hotmail.com";
    public static final String PASSWORD = "123";

    private User user;
    private UserDTO userDTO;

    @InjectMocks
    private UserResources resources;

    @Mock
    private UserServiceImpl service;

    @Mock
    private ModelMapper mapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //inicializa as instâncias
        this.startUser();

        //Instanciações necessárias para mockar a URI necessária no método create do userResources
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        //Precisamos "mockar" duas chamadas de método, primeiro o findById do Service e depois o "map" do Mapper
        Mockito.when(service.findById(Mockito.anyInt())).thenReturn(user);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resources.findById(ID);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        //Assegurando que o objeto que virá no corpo da resposta seja da classe UserDTO
        Assertions.assertEquals(UserDTO.class, response.getBody().getClass());

        //Assegurando os atributos do objeto retornado
        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NAME, response.getBody().getName());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());


    }

    @Test
    void findAllThenReturnAnListOfUserDTO() {
        Mockito.when(service.findAll()).thenReturn(List.of(user));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resources.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());

        //Assegurando que a resposta devolvida tenha o status 200 (OK)
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        //Assegurando que a resposta seja um ArrayList
        Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
        //Assegurando que o primeiro índice do array da resposta da minha lista seja um UserDTO.
        Assertions.assertEquals(UserDTO.class, response.getBody().get(0).getClass());

        //Validações dos atributos

        Assertions.assertEquals(ID, response.getBody().get(0).getId());
        Assertions.assertEquals(NAME, response.getBody().get(0).getName());
        Assertions.assertEquals(EMAIL, response.getBody().get(0).getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().get(0).getPassword());

    }

    @Test
    void whenCreateThenReturnCreated() {
        Mockito.when(service.create(Mockito.any())).thenReturn(user);

        ResponseEntity<UserDTO> response = resources.create(userDTO);


        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        //Assegurando que o código retornado é o 201 (CREATED)
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        //Assegurando que o location no headers do corpo da resposta não venha null
        Assertions.assertNotNull(response.getHeaders().get("Location"));

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}