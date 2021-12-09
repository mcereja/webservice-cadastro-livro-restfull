package br.com.imaster7.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imaster7.socialbooks.domain.Autor;

public interface Autores extends JpaRepository<Autor, Long> {

}
