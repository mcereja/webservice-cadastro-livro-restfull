package mcereja.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import mcereja.domain.Autor;
import mcereja.service.AutoresService;

/*
 * Cap. 2.19 add bean validation @Valid nos metodos salvar p/ o spring fazer a validação na camada de resource
 * 	 não na de persistencia pois assim exibe mensagens mais amigaveis ao usuario
 * Cap. 2.20 passa aceitar outros formatos Ex. xml do metodo listaTodos()
 *   obs. add no pom.xml entrada especifica, p/testar no postman incluir na aba headers 
 *      a key Accept e em value Application/xml 
 *      
 * IMPORTANTE!! Complemento do Cap. 4.6 CORS senão não aceita requisiçoes de dominios diferentes ou seja pelo navegador
 *   add a linha antMatchers(HttpMethod.OPTIONS, "/**").permitAll() e nos metodos das classes resources @CrossOrigin
 */

@RestController
@RequestMapping("/autores")
public class AutoresResources {

	@Autowired
	private AutoresService autoresService;
	
	@RequestMapping	( method = RequestMethod.GET, 
						produces = {MediaType.APPLICATION_JSON_VALUE, 
							MediaType.APPLICATION_XML_VALUE
						}
					)
	public ResponseEntity<List<Autor>> listaTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(autoresService.listaTodos());
	}
	
	// Validação @Valid add no Cap. 2.19
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> salvar(@Valid @RequestBody Autor autor) {
		autor = autoresService.salvar(autor);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(autor.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		Autor autor = autoresService.buscar(id);
		return ResponseEntity.status(HttpStatus.OK).body(autor);
	}
	
}
