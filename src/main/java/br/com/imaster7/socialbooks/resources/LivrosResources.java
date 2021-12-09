package br.com.imaster7.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.imaster7.socialbooks.domain.Comentario;
import br.com.imaster7.socialbooks.domain.Livro;
import br.com.imaster7.socialbooks.service.LivrosService;

/*
 * Cap. 2.19 add bean validation @Valid nos metodos salvar p/ o spring fazer a validação na camada de resource
 * 	 não na de persistencia pois assim exibe mensagens mais amigaveis ao usuario
 * Cap. 2.20 passa aceitar outros formatos Ex. xml ver AutoresResources.java
 * Cap. 3.1 add cache na busca por id
 * Cap. 3.2 captura o nome do usuario autenticado p/salvar o comentario
 * IMPORTANTE!! Complemento do Cap. 4.6 CORS senão não aceita requisiçoes de dominios diferentes ou seja pelo navegador
 *   add a linha antMatchers(HttpMethod.OPTIONS, "/**").permitAll() e nos metodos das classes resources @CrossOrigin
 */

@RestController
@RequestMapping("/livros")
public class LivrosResources {

	@Autowired
	private LivrosService livrosService;
	
	// Cap. 2.12 retorna o ResponseEntity e passa usasr livrosService
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity< List<Livro> > listar() {
		return ResponseEntity.status(HttpStatus.OK).body(livrosService.listarTodos()); 
	}
	
	// Validação @Valid add Cap. 2.19
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> salvar(@Valid @RequestBody Livro livro) {
		livro = livrosService.salvar(livro);
		
		//Cap. 2.10 Capta a uri do contexto para retornar ao usuario sobre como obter mais recursos
		//   no postman selecionar aba Body e no meio da tela a aba Headers(3) 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(livro.getId()).toUri();
		
		//  Retorna o status http correto 201		
		return ResponseEntity.created(uri).build();
	}
	
	// Cap. 2.10 add ResponseEntity p/tratar http 404
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		Livro livro = livrosService.buscar(id);
		
		CacheControl cacheCtr = CacheControl.maxAge(20, TimeUnit.SECONDS);
		
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheCtr).body(livro);
	}
	
	// Cap. 2.11 trata se tentar excluir um recurso nao existente
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		livrosService.remover(id);
		// Cap. 2.12 muda de OK para noContent
		return ResponseEntity.noContent().build();
		
	}
	
	/*
	 * Para garantir a não duplicacao fixamos o id a ser deletado
	 *  pq ele na realidade faz um merge no metodo save
	 */
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Livro livro,  @PathVariable Long id) {
		livro.setId(id);
		livrosService.atualizar(livro);
		//obs. no cap. 2.11 ele comenta que esse tipo de recurso tem que retornar noContent
		//  eu não concordo, acho que teria q fazer um tratamento como no delete
		return ResponseEntity.noContent().build();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/{id}/comentario", method = RequestMethod.POST)
	public ResponseEntity<Void> salvarComentario(@PathVariable("id") Long idLivro, @RequestBody Comentario comentario) {
		
		//Cap. 3.2 captura o nome do usuario autenticado p/salvar o comentario
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		comentario.setUsuario(auth.getName());
		
		livrosService.salvarComentario(idLivro, comentario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/{id}/comentario", method = RequestMethod.GET)
	public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable("id") Long idLivro) {
		List<Comentario> comentarios = livrosService.listarComentarios(idLivro);
		
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);
	}
}

