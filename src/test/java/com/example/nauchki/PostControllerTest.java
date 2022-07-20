package com.example.nauchki;

import com.example.nauchki.controller.PostController;
import com.example.nauchki.exceptions.DeniedException;
import com.example.nauchki.exceptions.ResourceNotFoundException;
import com.example.nauchki.jwt.TokenUtils;
import com.example.nauchki.mapper.PostMapper;
import com.example.nauchki.model.FileStorage;
import com.example.nauchki.model.Post;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.repository.PostRepo;
import com.example.nauchki.service.FileService;
import com.example.nauchki.service.PostService;
import com.example.nauchki.service.fileworker.UploadAndDeleteFileManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.oas.annotations.Parameter;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;

import java.io.StringWriter;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PostControllerTest {

    class PrincipalProxy implements Principal {
        String name;

        public PrincipalProxy(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @Autowired
    private DataSource dataSource;
    @MockBean
    private TokenUtils tokenUtils;
    @Autowired
    private PostMapper postMapper;
    @MockBean
    private UploadAndDeleteFileManager fileManager;
    @Autowired
    FileService fileService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepo postRepo;

    StringWriter writer;
    ObjectMapper objMapper;

    @Autowired
    private PostController postController;


    @Autowired
    Jackson2ObjectMapperBuilder mapperBuilder;

    String postNotFound_msg = "Статья не найдена";

    @BeforeAll
    void prepare(){

        List<Post> list = postRepo.findAll();
        if(list.size()==0) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("/init_db_scripts_post_test.sql"));
            populator.execute(dataSource);
        }

    }

    @BeforeEach
    public void prepareForTests(){

        writer = new StringWriter();
        objMapper = mapperBuilder.build();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Mockito.when(fileManager.deleteFile(any())).thenReturn(true);
        Mockito.when(fileManager.saveFile(any(), any())).thenReturn("test_path_init");
        fileService.setFileManager(fileManager);

        //добавление файлов под ролью ADMIN, для наполнения базы
        MockMultipartFile mockFile = new MockMultipartFile(
                "test_data_init", "filename_init.txt", "text/plain", "some data".getBytes()
        );
        PrincipalProxy principal = new PrincipalProxy("admin@test.ru");
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("ADMIN"));
        Assertions.assertEquals("test_path_init", postService.addImage(1L,
                "newImg_init",
                "new file init",
                mockFile,
                principal
        ));
        Assertions.assertEquals("test_path_init", postService.addImage(2L,
                "newImg_init",
                "new file init",
                mockFile,
                principal
        ));
        Assertions.assertEquals("test_path_init", postService.addImage(2L,
                "newImg_init",
                "new file init",
                mockFile,
                principal
        ));
        Assertions.assertEquals("test_path_init", postService.addImage(3L,
                "newImg_init",
                "new file init",
                mockFile,
                principal
        ));

        Mockito.when(fileManager.saveFile(any(), any())).thenReturn("test_path");


    }

    @Test
    void main() {
        //получение списка статей
        //либо всех, либо по тегам

        Post post = new Post();
        post.setTag("");

        List<PostDto> postDtoList = postController.main(post);
        Assertions.assertEquals(3, postDtoList.size());

        post.setTag("post1");
        postDtoList = postController.main(post);
        Assertions.assertEquals(2, postDtoList.size());

    }

    @Test
    void getPosts() {

        List<PostDto> postDtoList = postController.getPosts("post1");
        Assertions.assertEquals(2, postDtoList.size());

        postDtoList = postController.getPosts("post5");
        Assertions.assertEquals(0, postDtoList.size());

    }

    @Test
    void getTags() {

        List<String> tagList = postController.getTags();
        List<String> trueTags = Arrays.asList("post1", "post2");
        Assertions.assertEquals(trueTags.size(), tagList.size());
        Assertions.assertTrue(tagList.containsAll(trueTags));
        Assertions.assertTrue(trueTags.containsAll(tagList));

    }

    @Test
    void add() {

        List<Post> originList = postRepo.findAll();

        //AUTHOR
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("author@test.ru"));

        MockMultipartFile mockFile = new MockMultipartFile(
                "test_data", "filename.txt", "text/plain", "some data".getBytes()
        );

        Assertions.assertTrue(postController.add(
                "Add method testing",
                "Add method testing",
                "Add method testing",
                "testing",
                mockFile).getId() > 0
        );

        //USER
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("USER"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("author@test.ru"));

        Assertions.assertThrows(DeniedException.class, () -> postController.add(
                "Add method testing",
                "Add method testing",
                "Add method testing",
                "testing",
                mockFile)
        );

        //ADMIN
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("ADMIN"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("author@test.ru"));

        Assertions.assertTrue(postController.add(
                "Add method testing",
                "Add method testing",
                "Add method testing",
                "testing",
                mockFile).getId() > 0
        );

        //NO AUTHENTICATED
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList(""));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(DeniedException.class, () -> postController.add(
                "Add method testing",
                "Add method testing",
                "Add method testing",
                "testing",
                mockFile)
        );

        List<Post> currentList = postRepo.findAll();
        Assertions.assertEquals(originList.size()+2, currentList.size());

    }

    @Test
    void getPostService() {
        //удаление статьи по ID

        List<Post> originList = postRepo.findAll();

        //AUTHOR
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("author@test.ru"));
        //своя статья
        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                postController.getPostService(2L,
                        new PrincipalProxy("author@test.ru")));
        //не своя статья
        Assertions.assertThrows(DeniedException.class, () -> postController.getPostService(1L,
                        new PrincipalProxy("author@test.ru")));

        //USER
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("USER"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("author@test.ru"));
        Assertions.assertThrows(DeniedException.class, () -> postController.getPostService(1L,
                new PrincipalProxy("author@test.ru")));

        //ADMIN
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("ADMIN"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("author@test.ru"));
        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                postController.getPostService(1L,
                        new PrincipalProxy("author@test.ru")));

        //NO AUTHENTICATED
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList(""));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(DeniedException.class, () -> postController.getPostService(3L,
                new PrincipalProxy("author@test.ru")));

        List<Post> currentList = postRepo.findAll();
        Assertions.assertEquals(originList.size()-2, currentList.size());

    }

    @Test
    void addImage() {

        Post originPost = postRepo.findById(2L).orElseThrow(()->new ResourceNotFoundException(postNotFound_msg));
        int originCount = originPost.getFiles().size();

        MockMultipartFile mockFile = new MockMultipartFile(
                "test_data", "add_image.txt", "text/plain", "some data".getBytes()
        );

        //AUTHOR
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("author@test.ru"));
        //своя статья
        Assertions.assertEquals("test_path",
                postController.addImage(2L,
                        "new file",
                        "newfile",
                        mockFile,
                        new PrincipalProxy("author@test.ru")));
        //не своя статья
        Assertions.assertThrows(DeniedException.class, () -> postController.addImage(2L,
                "new file",
                "newfile",
                mockFile,
                new PrincipalProxy("user@test.ru")));

        //USER
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("USER"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("author@test.ru"));
        Assertions.assertThrows(DeniedException.class, () -> postController.addImage(2L,
                "new file",
                "newfile",
                mockFile,
                new PrincipalProxy("author@test.ru")));

        //ADMIN
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("ADMIN"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("author@test.ru"));
        Assertions.assertEquals("test_path",
                postController.addImage(2L,
                        "new file",
                        "newfile",
                        mockFile,
                        new PrincipalProxy("author@test.ru")));

        //NO AUTHENTICATED
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList(""));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(DeniedException.class, () -> postController.addImage(2L,
                "new file",
                "newfile",
                mockFile,
                new PrincipalProxy("author@test.ru")));

        Post currentPost = postRepo.findById(2L).orElseThrow(()->new ResourceNotFoundException(postNotFound_msg));
        Assertions.assertEquals(originCount+2, currentPost.getFiles().size());

    }

    @Test
    void delImages() {

        Post originPost = postRepo.findById(1L).orElseThrow(()->new ResourceNotFoundException(postNotFound_msg));
        int filesCount1 = originPost.getFiles().size();
        Long testImgId1 = originPost.getFiles().get(0).getId();

        originPost = postRepo.findById(2L).orElseThrow(()->new ResourceNotFoundException(postNotFound_msg));
        int filesCount2 = originPost.getFiles().size();
        Long testImgId2 = originPost.getFiles().get(0).getId();

        //удаление под юзером
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("USER"));
        Assertions.assertThrows( DeniedException.class, ()->postController.delImages(1L, testImgId1, new PrincipalProxy("user@test.ru")));
        //удаление под автором не своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        Assertions.assertThrows( DeniedException.class, ()->postController.delImages(1L, testImgId1, new PrincipalProxy("author@test.ru")));
        //удаление под автором своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        postController.delImages(2L, testImgId2, new PrincipalProxy("author@test.ru"));
        //удаление под админом не своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("ADMIN"));
        postController.delImages(1L, testImgId1, new PrincipalProxy("author@test.ru"));

        //проверка оставшихся файлов в статьях
        originPost = postRepo.findById(1L).orElseThrow(()->new ResourceNotFoundException(postNotFound_msg));
        Assertions.assertEquals(filesCount1-1, originPost.getFiles().size());
        originPost = postRepo.findById(2L).orElseThrow(()->new ResourceNotFoundException(postNotFound_msg));
        Assertions.assertEquals(filesCount2-1, originPost.getFiles().size());

    }
}