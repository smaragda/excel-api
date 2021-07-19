package cz.marcis.calculations.excelapi.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Slf4j
@Service
public class UploadService {


    @SneakyThrows
    public void moveToProjectDir(MultipartFile file) {
        log.info("Original file {} is being to upload to TEMP dir..", file.getOriginalFilename());

        Path destination = Path.of(
                LoadedExcel.EXCEL_DIR,
                LoadedExcel.EXCEL_FILE_NAME
        );
        file.transferTo(destination);
        log.info("..excel uploading done. destination: {}", destination);
    }
}
