package br.com.imaster7.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imaster7.socialbooks.domain.Livro;

public interface Livros extends JpaRepository<Livro, Long>{

}
