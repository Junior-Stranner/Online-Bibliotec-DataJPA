package br.com.judev.bibliotec.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.judev.bibliotec.dtos.Post;

public interface PostRepository extends PagingAndSortingRepository<Post , Long>{
    
}
