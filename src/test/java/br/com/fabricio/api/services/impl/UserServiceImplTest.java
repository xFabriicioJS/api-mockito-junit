package br.com.fabricio.api.services.impl;

import br.com.fabricio.api.domain.User;
import br.com.fabricio.api.domain.dto.UserDTO;
import br.com.fabricio.api.repositories.UserRepository;
import br.com.fabricio.api.services.exceptions.DataIntegratyViolationException;
import br.com.fabricio.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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


    //Testando o método findById, deve retornar uma instância de User
    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        //Mockando uma resposta para quando o método findById do repository for chamado
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        //Verificando se a minha resposta não é nula
        Assertions.assertNotNull(response);

        //"Assertando" que a minha resposta que veio lá do meu service realmente pertence à classe Usuário (User)
        Assertions.assertEquals(User.class, response.getClass());

        //Verificando se o "id" do meu objeto é realmente o "id" que passei como parâmetro
        Assertions.assertEquals(ID, response.getId());

        //Outras verificações
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());

    }

    //método que testa a exceção de objeto não encontrado
    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(
                new ObjectNotFoundException("Objeto não encontrado")
        );

        try{
            service.findById(ID);
        } catch (Exception ex){
            //Verifique se a exceção lançada é da classe ObjectNotFoundException
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            //Verificando se a mensagem que a exceção trará é a esperada
            assertEquals("Objeto não encontrado", ex.getMessage());

        }
    }


    //Método que testa o método create
    @Test
    void whenCreateReturnSuccess() {
        //Mockando o método save do repository, que irá retornar um User
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        //Passando um userDTO para o service, o nosso repository irá retornar um User
        User response = service.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }
    //Método que testa a exceção DataIntegrityViolationException que pode ser lançada ao criar
    //um usuário que já possui um email na base de dados
    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        //Mockando o método save do repository, que irá retornar um User
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        //Vamos colocar um try/catch para capturar a exceção
        try{
            optionalUser.get().setId(2);
            service.create(userDTO);
        } catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals("Email já está cadastrado no sistema! Por favor insira um outro email.",
                    ex.getMessage());
        }
    }

    //Método que testa o método findAll
    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        //Mockando o método findAll lá do repository, desse jeito o método findAll do repository só irá
        //retornar um usuário para o SERVICE
        Mockito.when(repository.findAll()).thenReturn(List.of(user));

        //SERVICE
        List<User> response = service.findAll();

        //Assegurando que a resposta não será nula
        assertNotNull(response);

        //Assegurando que a lista só tenha um elemento
        assertEquals(1, response.size());

        //Assegurando que a classe do objeto da response seja User
        assertEquals(User.class, response.get(0).getClass());

        assertEquals(ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        assertEquals(EMAIL, response.get(0).getEmail());
        assertEquals(PASSWORD, response.get(0).getPassword());
    }

    //Método que testa o método update
    @Test
    void whenUpdateThenReturnSuccess() {
        //Mockando o método save do repository, que irá retornar um User
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        //Passando um userDTO para o service, o nosso repository irá retornar um User
        User response = service.update(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    //Método que testa a exceção que pode ser lançada no "update"
    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        //Mockando o método save do repository, que irá retornar um User
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        //Vamos colocar um try/catch para capturar a exceção
        try{
            optionalUser.get().setId(2);
            service.create(userDTO);
        } catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals("Email já está cadastrado no sistema! Por favor insira um outro email.",
                    ex.getMessage());
        }
    }

    @Test
    void findByEmail() {
    }

    //Método que testa o delete, quando o delete é bem-sucedido.
    @Test
    void deleteWithSuccess() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
        Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());

        //Chamando o delete do service
        service.delete(ID);

        //Verificando e Assertando se o repository foi realmente chamado apenas uma única vez
        Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.anyInt());
    }

    @Test
    void deleteWithObjectNotFoundException(){
        Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try{
            service.delete(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());

        }
    }


    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}