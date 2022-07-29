package ru.greenatom.atomskils.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.greenatom.atomskils.pojo.FileResponse;
import ru.greenatom.atomskils.services.interfaces.StorageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/files")
public class FileController {

    private StorageService storageService;

    public FileController(@Qualifier("DBStorageService") StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    @ResponseBody
    public List<FileResponse> listAllFiles(Model model) {
        List<FileResponse> fileResponses = new ArrayList<>();
        storageService.loadAll().forEach(path -> {
            fileResponses.add(new FileResponse(path.toString(), ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/files/download/")
                    .path(path.getFileName().toString()).toUriString(), "", 0));
        });
        return fileResponses;
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @PostMapping("/upload-file")
    @ResponseBody
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String name = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(name)
                .toUriString();

        return new FileResponse(name, uri, file.getContentType(), file.getSize());
    }

    @PostMapping("/upload-multiple-files")
    @ResponseBody
    public List<FileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
}