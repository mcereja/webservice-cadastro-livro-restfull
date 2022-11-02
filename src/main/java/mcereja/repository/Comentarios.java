package mcereja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mcereja.domain.Comentario;

public interface Comentarios extends JpaRepository<Comentario, Long> {

}
