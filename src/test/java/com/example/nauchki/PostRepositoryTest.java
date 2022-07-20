package com.example.nauchki;

import com.example.nauchki.model.Post;
import com.example.nauchki.repository.PostRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;


@DataJpaTest
@ActiveProfiles("test")
public class PostRepositoryTest {

    @Autowired
    private TestEntityManager tem;

    @Autowired
    private PostRepo postRepo;

    @Test
    public void findAllTag(){
        tem.persist(Post.builder()
                .tag("test1")
                .title("Test post")
                .build()
        );
        tem.persist(Post.builder()
                .tag("test2")
                .title("Test post")
                .build()
        );
        List<String> allTags = Arrays.asList("test2", "test1");
        List<String> findTags = postRepo.findAllTag();
        Assertions.assertTrue(allTags.size() == findTags.size() && allTags.containsAll(findTags) && findTags.containsAll(allTags));
    }

    @Test
    public void findByTag(){
        Post p1 = Post.builder()
                .tag("test1")
                .title("Test post")
                .build();
        Post p2 = Post.builder()
                .tag("test2")
                .title("Test post")
                .build();
        tem.persist(p1);
        tem.persist(p2);

        List<Post> allTags = Arrays.asList(p1);

        List<Post> findTags = postRepo.findByTag("test1");
        Assertions.assertTrue(allTags.size() == findTags.size() && allTags.containsAll(findTags) && findTags.containsAll(allTags));

        findTags = postRepo.findByTag("test3");
        Assertions.assertEquals(0, findTags.size());

    }

}
