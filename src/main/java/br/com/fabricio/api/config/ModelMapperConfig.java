package br.com.fabricio.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	//Com esse bean, permitiremos ao Spring que gerencie injencao dessa instancia, ou seja, conseguiremos utilizar o @Autowired.
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	
}
