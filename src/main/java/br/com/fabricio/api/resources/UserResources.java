package br.com.fabricio.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj){
		User newObj = service.create(obj);
		
		//Criando a URI do Id criado para o usuário
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO obj){
		//Garantindo que o id será atribuido ao novo objeto.
		obj.setId(id);

		User newObj = service.update(obj);

		return ResponseEntity.ok().body(mapper.map(newObj, UserDTO.class));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable Integer id){
		service.delete(id);

		return ResponseEntity.noContent().build();
	}



}
