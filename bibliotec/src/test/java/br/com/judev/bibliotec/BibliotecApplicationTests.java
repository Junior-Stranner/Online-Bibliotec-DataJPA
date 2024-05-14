/*package br.com.judev.bibliotec;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.judev.bibliotec.dtos.Post;
import br.com.judev.bibliotec.service.PostService;
import lombok.AllArgsConstructor;

@ContextConfiguration
@SpringBootTest
@AllArgsConstructor
@ExtendWith(SpringExtension.class)
class BibliotecApplicationTests {
	static final Post POST_0 = new Post(1L, "Se inscreve no canal", "@giulianabezerra");
	static final Post POST_1 = new Post(2L, "Ativa as notificações", "@giulianabezerra");

	public PostService postService;

	@Test
	void testListPostsPaginated() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Post> pagePost = postService.listPosts(pageable);

		assertEquals(2, pagePost.getContent().size());
		assertEquals(POST_0, pagePost.getContent().get(0));
		assertEquals(POST_1, pagePost.getContent().get(1));
	}


}*/
