package cz.marcis.calculations.excelapi.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
public class UploadService {


    @SneakyThrows
    public void moveToProjectDir(MultipartFile file) {
        file.transferTo(Path.of("uploaded"));
    }
}
