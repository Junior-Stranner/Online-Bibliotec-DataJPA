package br.com.judev.bibliotec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.Post;
import br.com.judev.bibliotec.repository.PostRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

       public PostRepository postRepository;

    
    public Page<Post> listPosts(Pageable Pageable){
        return postRepository.findAll(Pageable);
    }
}
