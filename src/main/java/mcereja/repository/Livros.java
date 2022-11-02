package mcereja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mcereja.domain.Livro;

public interface Livros extends JpaRepository<Livro, Long>{

}
