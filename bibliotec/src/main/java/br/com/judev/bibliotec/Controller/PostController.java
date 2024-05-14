package br.com.judev.bibliotec.Controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.judev.bibliotec.dtos.Post;
import br.com.judev.bibliotec.service.PostService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vq/api/posts")
@AllArgsConstructor
public class PostController {

    public PostService postService;
    
    @GetMapping
    public List<Post> listPosts(Pageable pageable){
        return postService.listPosts(pageable).getContent();

    }
}
