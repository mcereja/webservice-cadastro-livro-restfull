package mcereja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mcereja.domain.Autor;

public interface Autores extends JpaRepository<Autor, Long> {

}
