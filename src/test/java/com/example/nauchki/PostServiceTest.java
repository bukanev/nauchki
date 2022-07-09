package com.example.nauchki;

import com.example.nauchki.mapper.PostMapper;
import com.example.nauchki.model.FileStorage;
import com.example.nauchki.model.Post;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.repository.PostRepo;
import com.example.nauchki.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    public PostRepo postRepo;

    StringWriter writer;
    ObjectMapper objMapper;
    Post samplePost1;
    Post samplePost2;
    Post wantedPost1;
    Post wantedPost2;
    User sampleUser;
    FileStorage sampleFile1;
    FileStorage sampleFile2;
    FileStorage sampleFile3;

    @BeforeEach
    public void tuneTest(){

        writer = new StringWriter();
        objMapper = new ObjectMapper();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);

        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setLogin("testUser");
        sampleUser.setUsername("Test User");
        sampleUser.setEmail("user@test.ru");

        sampleFile1 = new FileStorage();
        sampleFile1.setId(1L);
        sampleFile1.setName("name");
        sampleFile1.setType("type");
        sampleFile1.setTags("tags");
        sampleFile1.setDescription("description");
        sampleFile1.setExternalPath("externalPath");
        sampleFile1.setSize(111L);

        sampleFile2 = new FileStorage();
        sampleFile2.setId(2L);
        sampleFile2.setName("name2");
        sampleFile2.setType("type2");
        sampleFile2.setTags("tags2");
        sampleFile2.setDescription("description2");
        sampleFile2.setExternalPath("externalPath2");
        sampleFile2.setSize(222L);

        sampleFile3 = new FileStorage();
        sampleFile3.setId(3L);
        sampleFile3.setName("name3");
        sampleFile3.setType("type3");
        sampleFile3.setTags("tags3");
        sampleFile3.setDescription("description3");
        sampleFile3.setExternalPath("externalPath3");
        sampleFile3.setSize(333L);

        samplePost1 = Post.builder()
                .id(1L)
                .author(sampleUser)
                .tag("tag")
                .text("text")
                .title("title")
                .subtitle("subtitle")
                .images(Arrays.asList(sampleFile1, sampleFile2))
                .build();

        samplePost2 = Post.builder()
                .id(1L)
                .author(sampleUser)
                .tag("tag")
                .text("text")
                .title("title")
                .subtitle("subtitle")
                .images(Arrays.asList(sampleFile1, sampleFile2))
                .build();

        wantedPost1 = Post.builder()//для успешного поиска
                .tag("tag")
                .build();
        wantedPost2 = Post.builder()//для неуспешного поиска
                .tag("tag2")
                .build();

        Mockito.doReturn(Arrays.asList(samplePost1)).when(postRepo).findAll(Example.of(wantedPost1));
        Mockito.doReturn(Arrays.asList()).when(postRepo).findAll(Example.of(wantedPost2));
        Mockito.doReturn(Arrays.asList(samplePost1, samplePost2)).when(postRepo).findAll();

        Mockito.doReturn(samplePost1).when(postRepo).findById(1L);
        Mockito.doReturn(samplePost2).when(postRepo).findById(2L);

    }

    @Test
    public void getPost() throws IOException {

        PostDto postDto = postMapper.toDto(samplePost1);
        List<PostDto> trueList = Arrays.asList(postDto);
        writer = new StringWriter();
        objMapper.writeValue(writer, trueList);
        String trueListJSON = writer.toString();

        //возврат результата
        List<PostDto> retList = postService.getPost(wantedPost1);
        writer = new StringWriter();
        objMapper.writeValue(writer, retList);
        String retListJSON = writer.toString();

        //чтобы не писать методы equals используем JSON представление объекта
        Assertions.assertEquals(trueListJSON, retListJSON);

        //пустой результат
        retList = postService.getPost(wantedPost2);
        writer = new StringWriter();
        objMapper.writeValue(writer, retList);
        retListJSON = writer.toString();

        writer = new StringWriter();
        objMapper.writeValue(writer, new ArrayList<PostDto>());
        String emptyListJSON = writer.toString();

        Assertions.assertEquals(emptyListJSON, retListJSON);

    }

    @Test
    void getAllPost() throws IOException {

        PostDto postDto = postMapper.toDto(samplePost1);
        PostDto postDto2 = postMapper.toDto(samplePost2);
        List<PostDto> trueList = Arrays.asList(postDto, postDto2);
        writer = new StringWriter();
        objMapper.writeValue(writer, trueList);
        String trueListJSON = writer.toString();

        List<PostDto> retList = postService.getAllPost();
        writer = new StringWriter();
        objMapper.writeValue(writer, retList);
        String retListJSON = writer.toString();

        Assertions.assertEquals(trueListJSON, retListJSON);

    }

    @Test
    void deletePost() {

    }

    @Test
    void addPost() {
    }

    @Test
    void getAllTags() {
    }

    @Test
    void addImage() {
    }

    @Test
    void delImage() {
    }

    @Test
    void delAllImages() {
    }
}
