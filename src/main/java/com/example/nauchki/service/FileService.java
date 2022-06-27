package com.example.nauchki.service;

import com.example.nauchki.exceptions.ResourceNotFoundException;
import com.example.nauchki.model.FileStorage;
import com.example.nauchki.model.Post;
import com.example.nauchki.repository.FileStorageRepository;
import com.example.nauchki.repository.PostRepo;
import com.example.nauchki.service.fileworker.UploadAndDeleteFileManager;
import com.example.nauchki.utils.FileContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class FileService {
    private final UploadAndDeleteFileManager fileManager;
    private final FileStorageRepository fileStorageRepository;
    private final PostRepo postRepo;

    /**
     * инициализация записи файла на основе загруженного файла с клиента
     * присвоение файлу идентификатора
     * @param storeFile - запись файла в базе
     * @param mpFile - загруженный с клиента файл
     * @return заполненная запись файла
     */
    public FileStorage initStorageFile(FileStorage storeFile, MultipartFile mpFile){
        storeFile.setName(mpFile.getOriginalFilename());
        storeFile.setType(mpFile.getContentType());
        storeFile.setSize(mpFile.getSize());
        String externalId = storeFile.getExternalId();
        if(externalId==null || externalId.isEmpty()) {
            storeFile.setExternalId(UUID.randomUUID().toString()); //это внешний ключ для облачного хранилища
        }
        return storeFile;
    }

    /**
     * изменение записи файла в базе и во внешнем хранилище
     * @param file - загруженный файл с клиента
     * @param fileId - идентификатор файла в базе, для поиска файла
     * @return путь к файлу во внешнем хранилище
     */
    public String changeFile(MultipartFile file, Long fileId) {
        String path="";
        FileStorage fileStorage = fileStorageRepository.findById(fileId).orElseThrow(
                ()->new ResourceNotFoundException("File with id '" + fileId + "' not found")
        );
        if(fileManager.deleteFile(fileStorage.getExternalId())){
            initStorageFile(fileStorage, file);
            path = fileManager.saveFile(file, fileStorage.getExternalId());
            fileStorage.setExternalPath(path);
            fileStorageRepository.save(fileStorage);
        }
        return path;
    }

    /**
     * изменение свойств файла без изменения его содержимого
     * @param fileId - идентификатор файла в базе, для поиска файла
     * @param tags - новые теги
     * @param description - новое описание
     */
    public void changeFileInfo(Long fileId, String tags, String description) {
        FileStorage fileStorage = fileStorageRepository.findById(fileId).orElseThrow(
                ()->new ResourceNotFoundException("File with id '" + fileId + "' not found")
        );
        fileStorage.setDescription(description);
        fileStorage.setTags(tags);
        fileStorageRepository.save(fileStorage);
    }

    /**
     * добавление комментария к файлу
     * @param fileId - идентификатор файла в базе, для поиска файла
     * @param userId - идентификатор пользователя оставившего комментарий
     * @param Comment - комментарий
     * @return идентификатор добавленного комментария
     */
    public Long addComment(Long fileId, Long userId, String Comment){
        return 0L;
    }

    /**
     * удаление комментария
     * @param fileId - идентификатор файла в базе, для поиска файла
     * @param commentId - идентификатор удаляемого комментария в базе, для поиска файла
     */
    public void deleteComment(Long fileId, Long commentId){

    }

    /**
     * загрузка прикрепленного к сущности файла
     * @param file - загруженный с клиента файл
     * @param entity - сущность к которой необходимо прикрепить файл
     * @return новый путь к файлу во внешнем хранилище
     */
    public String saveAttachedFile(MultipartFile file, FileContainer entity) {
        FileStorage newFile = new FileStorage();
        newFile = initStorageFile(newFile, file);
        String path;
        path = fileManager.saveFile(file, newFile.getExternalId());
        if (!path.isEmpty()) {
            newFile.setOwnerType(entity.getEntityType());
            newFile.setOwnerId(entity.getEntityId());
            newFile.setExternalPath(path);
            entity.getFiles().add(newFile);
            fileStorageRepository.save(newFile);
        }
       return path;
    }

    public String saveAttachedFilePost(MultipartFile file, Post entity) {
        FileStorage newFile = new FileStorage();
        newFile = initStorageFile(newFile, file);
        String path;
        path = fileManager.saveFile(file, newFile.getExternalId());
        if (!path.isEmpty()) {
            newFile.setOwnerType(entity.getEntityType());
            newFile.setOwnerId(entity.getEntityId());
            newFile.setExternalPath(path);
            entity.getFiles().add(newFile);
            fileStorageRepository.save(newFile);
            postRepo.save(entity);

        }
       return path;
    }

    /**
     * загрузка прикрепленного к сущности файла с дополнительной информацией
     * @param file - загруженный с клиента файл
     * @param entity - сущность к которой необходимо прикрепить файл
     * @param tags - теги
     * @param description - описание файла
     * @return новый путь к файлу во внешнем хранилище
     */
    public String saveAttachedFile(MultipartFile file, FileContainer entity, String tags, String description) {
        FileStorage newFile = new FileStorage();
        newFile = initStorageFile(newFile, file);
        String path;
        path = fileManager.saveFile(file, newFile.getExternalId());
        if (!path.isEmpty()) {
            newFile.setOwnerType(entity.getEntityType());
            newFile.setOwnerId(entity.getEntityId());
            newFile.setExternalPath(path);
            newFile.setTags(tags);
            newFile.setDescription(description);
            entity.getFiles().add(newFile);
            fileStorageRepository.save(newFile);
        }
        return path;
    }

    /**
     * загрузка файла в базу без прикрепления к сущности
     * @param file - загруженный с клиента файл
     * @return новый путь к файлу во внешнем хранилище
     */
    public String saveFile(MultipartFile file) {
        FileStorage newFile = new FileStorage();
        newFile = initStorageFile(newFile, file);
        String path;
        path = fileManager.saveFile(file, newFile.getExternalId());
        if (!path.isEmpty()) {
            fileStorageRepository.save(newFile);
        }
        return path;
    }

    /**
     * удаление прикрепленного к сущности файла
     * принадлежность файла к сущности, должна производиться в сервисе обслуживающим сущность, перед вызовом метода
     * @param fileId - идентификатор файла в базе, для поиска файла
     * @param entity - сущность у которой удаляется файл
     * @return в случае успеха true
     */
    public boolean deleteAttachedFile(Long fileId, FileContainer entity) {
        FileStorage atFile = fileStorageRepository.findById(fileId).orElseThrow(
                ()->new ResourceNotFoundException("File with id '" + fileId + "' not found"));
        boolean isOk = fileManager.deleteFile(atFile.getExternalId());
        if (isOk && entity != null) {
            List<FileStorage> fileStorage = entity.getFiles();
            fileStorage = fileStorage.stream()
                    .filter( v-> {
                                boolean isMatch= v.getId().equals(fileId);
                                if(isMatch){
                                    fileStorageRepository.delete(v);
                                }
                                return !isMatch;
                            }
                    )
                    .collect(Collectors.toList());
            entity.getFiles().clear();
            entity.getFiles().addAll(fileStorage);
        }
        return isOk;
    }

    /**
     * удаление файла, который не принадлежит сущности
     * @param fileId - идентификатор файла в базе, для поиска файла
     * @return в случае успеха true
     */
    public boolean deleteFile(Long fileId) {
        FileStorage atFile = fileStorageRepository.findById(fileId).orElseThrow(
                ()->new ResourceNotFoundException("File with id '" + fileId + "' not found"));
        boolean isOk = fileManager.deleteFile(atFile.getExternalId());
        if (isOk) {
            fileStorageRepository.delete(atFile);
        }
        return isOk;
    }

    /**
     * удаление у сущности всех прикрепленных файлов
     * @param entity - сущность у которой удаляются файлы
     * @return в случае успеха true
     */
    public boolean deleteAllAttachedFiles(FileContainer entity) {
        List<FileStorage> fileStorage = entity.getFiles();
        fileStorage.stream()
                .forEach(v-> {
                            if(fileManager.deleteFile(v.getExternalId())){
                                fileStorageRepository.delete(v);
                            }
                        }
                );
        entity.getFiles().clear();
        return true;
    }
}
