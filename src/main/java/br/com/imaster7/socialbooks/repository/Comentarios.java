package br.com.imaster7.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imaster7.socialbooks.domain.Comentario;

public interface Comentarios extends JpaRepository<Comentario, Long> {

}
