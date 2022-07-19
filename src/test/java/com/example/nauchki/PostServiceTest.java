package com.example.nauchki;

import com.example.nauchki.exceptions.DeniedException;
import com.example.nauchki.exceptions.ResourceNotFoundException;
import com.example.nauchki.jwt.TokenUtils;
import com.example.nauchki.mapper.PostMapper;
import com.example.nauchki.model.FileStorage;
import com.example.nauchki.model.Post;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.repository.FileStorageRepository;
import com.example.nauchki.repository.PostRepo;
import com.example.nauchki.repository.UserRepository;
import com.example.nauchki.service.FileService;
import com.example.nauchki.service.PostService;
import com.example.nauchki.service.fileworker.UploadAndDeleteFileManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.StringWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostServiceTest {

    class PrincipalProxy implements Principal{
        String name;

        public PrincipalProxy(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @MockBean
    private UploadAndDeleteFileManager fileManager;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    FileService fileService;
    @Autowired
    private PostService postService;
    @MockBean
    private TokenUtils tokenUtils;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private FileStorageRepository fileStorageRepository;
    @Autowired
    private UserRepository userRepo;

    StringWriter writer;
    ObjectMapper objMapper;

    @Autowired
    Jackson2ObjectMapperBuilder mapperBuilder;

    @Autowired
    private DataSource dataSource;

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
        Assertions.assertEquals("test_path_init", postService.addImage(3L,
                "newImg_init",
                "new file init",
                mockFile,
                principal
        ));

        Mockito.when(fileManager.saveFile(any(), any())).thenReturn("test_path");


    }

    @BeforeAll
    void prepare(){

        List<Post> list = postRepo.findAll();
        if(list.size()==0) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("/init_db_scripts_post_test.sql"));
            populator.execute(dataSource);
        }

    }


    @Test
    @Transactional
    void addPost() {

        List<Post> originList = postRepo.findAll();
        int originCount = originList.size();

        User user = userRepo.findById(1L).orElseThrow(()->new ResourceNotFoundException("Пользователь не найден"));

        Post samplePost1 = new Post();
        samplePost1.setAuthor(user);
        samplePost1.setSubtitle("New post subtitle");
        samplePost1.setText("New post text");
        samplePost1.setTag("post1");
        samplePost1.setTitle("New Post");

        Post samplePost2 = new Post();
        samplePost2.setAuthor(user);
        samplePost2.setSubtitle("New post subtitle");
        samplePost2.setText("New post text");
        samplePost2.setTag("post1");
        samplePost2.setTitle("New Post");

        Post samplePost3 = new Post();
        samplePost3.setAuthor(user);
        samplePost3.setSubtitle("New post subtitle");
        samplePost3.setText("New post text");
        samplePost3.setTag("post1");
        samplePost3.setTitle("New Post");

        MockMultipartFile  mockFile = new MockMultipartFile(
                "data", "filename.txt", "text/plain", "some data".getBytes()
        );

        String s = fileManager.saveFile(mockFile,"133");

        //ADMIN
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("ADMIN"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("admin@test.ru"));
        Long newPostID1 = postService.addPost(samplePost1, mockFile).getId();
        Assertions.assertTrue(newPostID1>0);
        //AUTHOR
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("admin@test.ru"));
        Long newPostID2 = postService.addPost(samplePost2, mockFile).getId();
        Assertions.assertTrue(newPostID2>0);
        //USER
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("USER"));
        Mockito.when(tokenUtils.getPrincipalName()).thenReturn(Optional.of("user@test.ru"));
        Assertions.assertThrows( DeniedException.class, ()->postService.addPost(samplePost3, mockFile));

        Assertions.assertEquals(originCount +2, postRepo.findAll().size());

        Optional<Post> post = postRepo.findById(newPostID1);
        Assertions.assertTrue(post.isPresent());

        Post newPostModel = post.get();
        Assertions.assertEquals(1, newPostModel.getFiles().size());
        Assertions.assertEquals("test_path", newPostModel.getFiles().get(0).getExternalPath());

    }

    @Test
    @Transactional
    void addImage() {

        int imageCount1 = postRepo.findById(1L).orElse(new Post()).getImages().size();
        int imageCount2 = postRepo.findById(2L).orElse(new Post()).getImages().size();

        //добавление под ролью USER
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("USER"));
        Assertions.assertThrows(DeniedException.class,
                ()->postService.addImage(1L,
                "newImg",
                "new file",
                new MockMultipartFile(
                        "data", "filename1.txt", "text/plain", "some data".getBytes()
                ),
                new PrincipalProxy("admin@test.ru"))
        );

        //добавление под ролью AUTHOR, но не автором
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        Assertions.assertThrows(DeniedException.class,
                ()->postService.addImage(1L,
                        "newImg",
                        "new file",
                        new MockMultipartFile(
                                "test_data", "filename2.txt", "text/plain", "some data".getBytes()
                        ),
                        new PrincipalProxy("user@test.ru"))
        );

        //добавление под ролью AUTHOR, автором
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("ADMIN"));
        Assertions.assertEquals("test_path", postService.addImage(1L,
                "newImg",
                "new file",
                new MockMultipartFile(
                        "test_data", "filename3.txt", "text/plain", "some data".getBytes()
                ),
                new PrincipalProxy("admin@test.ru"))
        );

        //добавление под ролью ADMIN, не автором
        Assertions.assertEquals("test_path", postService.addImage(2L,
                "newImg",
                "new file",
                new MockMultipartFile(
                        "test_data", "filename4.txt", "text/plain", "some data".getBytes()
                ),
                new PrincipalProxy("admin@test.ru"))
        );

        Post postModel = postRepo.findById(1L).orElse(new Post());
        Assertions.assertEquals(imageCount1+1, postModel.getImages().size());
        Assertions.assertEquals("filename3.txt", postModel.getImages().get(imageCount1).getName());
        Assertions.assertEquals("test_path", postModel.getImages().get(imageCount1).getExternalPath());

        postModel = postRepo.findById(2L).orElse(new Post());
        Assertions.assertEquals(imageCount2+1, postModel.getImages().size());
        Assertions.assertEquals("filename4.txt", postModel.getImages().get(imageCount2).getName());

    }

    @Test
    @Transactional
    public void getPost() throws IOException {

        PostDto postDto1 = postMapper.toDto(postRepo.getById(1L));
        PostDto postDto3 = postMapper.toDto(postRepo.getById(3L));
        List<PostDto> trueList = Arrays.asList(postDto1, postDto3);
        writer = new StringWriter();
        objMapper.writeValue(writer, trueList);
        String trueListJSON = writer.toString();

        //поиск поста
        Post post = new Post();
        post.setTag("post1");
        List<PostDto> retList = postService.getPost(post);
        writer = new StringWriter();
        objMapper.writeValue(writer, retList);
        String retListJSON = writer.toString();

        //чтобы не писать методы equals используем JSON представление объекта
        Assertions.assertEquals(trueListJSON, retListJSON);

        //пустой результат
        post.setTag("empty_post");
        retList = postService.getPost(post);
        writer = new StringWriter();
        objMapper.writeValue(writer, retList);
        retListJSON = writer.toString();

        writer = new StringWriter();
        objMapper.writeValue(writer, new ArrayList<PostDto>());
        String emptyListJSON = writer.toString();

        Assertions.assertEquals(emptyListJSON, retListJSON);

    }

    @Test
    @Transactional
    void getAllPost() throws IOException {

        List<PostDto> retList = postService.getAllPost();
        writer = new StringWriter();
        objMapper.writeValue(writer, retList);
        String retListJSON = writer.toString();

        Assertions.assertEquals(3, retList.size());

    }

    @Test
    @Transactional
    void deletePost() {

        List<Post> originList = postRepo.findAll();
        int filesCount1 = 0;
        int filesCount2 = 0;
        for (Post p: originList) {
            if(p.getId()==1) filesCount1 = p.getFiles().size();
            if(p.getId()==2) filesCount2 = p.getFiles().size();
        }
        List<FileStorage> originFiles = fileStorageRepository.findAll();

        //удаление под юзером
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("USER"));
        Assertions.assertThrows( DeniedException.class, ()->postService.deletePost(1L, new PrincipalProxy("user@test.ru")));
        //удаление под автором не своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        Assertions.assertThrows( DeniedException.class, ()->postService.deletePost(1L, new PrincipalProxy("author@test.ru")));
        //удаление под автором своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        Assertions.assertTrue( postService.deletePost(2L, new PrincipalProxy("author@test.ru")));
        //удаление под админом не своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("ADMIN"));
        Assertions.assertTrue( postService.deletePost(1L, new PrincipalProxy("author@test.ru")));

        List<Post> currentList = postRepo.findAll();
        List<FileStorage> currentFiles = fileStorageRepository.findAll();

        //проверка оставшихся статей
        Assertions.assertEquals(originList.size()-2, currentList.size());
        //проверка оставшихся файлов
        Assertions.assertEquals(originFiles.size() - filesCount1 - filesCount2, currentFiles.size());

    }


    @Test
    @Transactional
    void delImage() {

        List<FileStorage> originFiles = fileStorageRepository.findAll();

        Post originPost = postRepo.findById(1L).orElseThrow(()->new ResourceNotFoundException("Not found"));
        int filesCount1 = originPost.getFiles().size();
        Long testImgId1 = originPost.getFiles().get(0).getId();

        originPost = postRepo.findById(2L).orElseThrow(()->new ResourceNotFoundException("Not found"));
        int filesCount2 = originPost.getFiles().size();
        originFiles = fileStorageRepository.findAll();
        Long testImgId2 = originPost.getFiles().get(0).getId();

        //удаление под юзером
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("USER"));
        Assertions.assertThrows( DeniedException.class, ()->postService.delImage(1L, testImgId1, new PrincipalProxy("user@test.ru")));
        //удаление под автором не своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        Assertions.assertThrows( DeniedException.class, ()->postService.delImage(1L, testImgId1, new PrincipalProxy("author@test.ru")));
        //удаление под автором своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        postService.delImage(2L, testImgId2, new PrincipalProxy("author@test.ru"));
        //удаление под админом не своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("ADMIN"));
        postService.delImage(1L, testImgId1, new PrincipalProxy("author@test.ru"));

        //проверка оставшихся файлов в статьях
        originPost = postRepo.findById(1L).orElseThrow(()->new ResourceNotFoundException("Not found"));
        Assertions.assertEquals(filesCount1-1, originPost.getFiles().size());
        originPost = postRepo.findById(2L).orElseThrow(()->new ResourceNotFoundException("Not found"));
        Assertions.assertEquals(filesCount2-1, originPost.getFiles().size());
        //проверка оставшихся файлов
        List<FileStorage> currentFiles = fileStorageRepository.findAll();
        Assertions.assertEquals(originFiles.size() - 2, currentFiles.size());

    }

    @Test
    @Transactional
    void delAllImages() {

        List<Post> originList = postRepo.findAll();
        int filesCount1 = 0;
        int filesCount2 = 0;
        for (Post p: originList) {
            if(p.getId()==1) filesCount1 = p.getFiles().size();
            if(p.getId()==2) filesCount2 = p.getFiles().size();
        }
        List<FileStorage> originFiles = fileStorageRepository.findAll();

        //удаление под юзером
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("USER"));
        Assertions.assertThrows( DeniedException.class, ()->postService.delAllImages(1L, new PrincipalProxy("user@test.ru")));
        //удаление под автором не своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        Assertions.assertThrows( DeniedException.class, ()->postService.delAllImages(1L, new PrincipalProxy("author@test.ru")));
        //удаление под автором своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("AUTHOR"));
        postService.delAllImages(2L, new PrincipalProxy("author@test.ru"));
        //удаление под админом не своей статьи
        Mockito.when(tokenUtils.getRoles()).thenReturn(Arrays.asList("ADMIN"));
        postService.delAllImages(1L, new PrincipalProxy("author@test.ru"));

        List<Post> currentList = postRepo.findAll();
        List<FileStorage> currentFiles = fileStorageRepository.findAll();

        //проверка оставшихся файлов в статьях
        Post originPost = postRepo.findById(1L).orElseThrow(()->new ResourceNotFoundException("Not found"));
        Assertions.assertEquals(0, originPost.getFiles().size());
        originPost = postRepo.findById(2L).orElseThrow(()->new ResourceNotFoundException("Not found"));
        Assertions.assertEquals(0, originPost.getFiles().size());
        //проверка оставшихся файлов
        Assertions.assertEquals(originFiles.size() - filesCount1 - filesCount2, currentFiles.size());

    }

    @Test
    void getAllTags() {
        List<String> allTags = postService.getAllTags();
        List<String> trueTags = Arrays.asList("post1", "post2");
        Assertions.assertEquals(trueTags.size(), allTags.size());
        Assertions.assertTrue(trueTags.containsAll(allTags));
        Assertions.assertTrue(allTags.containsAll(trueTags));
    }
}
