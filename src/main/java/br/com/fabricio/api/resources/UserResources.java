package br.com.fabricio.api.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabricio.api.domain.User;
import br.com.fabricio.api.domain.dto.UserDTO;
import br.com.fabricio.api.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserResources {

	@Autowired
	ModelMapper mapper;

	@Autowired
	UserService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
		
		//Utilizando o ModelMapper para passar a classe UserDTO.
		return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class));
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User> lista = service.findAll();
		
		List<UserDTO> listaDTO = lista.stream().map((item)-> mapper.map(item, UserDTO.class)).collect(Collectors.toList());
		
		return ResponseEntity.ok(listaDTO);
	}

}
