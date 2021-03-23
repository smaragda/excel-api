package cz.marcis.calculations.excelapi.controller;

import cz.marcis.calculations.excelapi.service.SheetService;
import cz.marcis.calculations.excelapi.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DataController {

    private final SheetService service;
    private final UploadService uploadService;

    /**
     * Gets calculated sheet result with evaluated cells.
     *
     * @param inputCellsSheet sheet with populated data values - NOT with formulas, just values.
     * @return calculated sheet result - evaluated cells
     */
    @PostMapping(value = "/calc")
    public Map<String, Number> getCalculatedSheet(@RequestBody String inputCellsSheet) {
        return service.evaluateSheet(inputCellsSheet);
    }

    @PostMapping(value = "/upload", produces = "plain/text")
    public String submit(@RequestParam("files") MultipartFile file) {

        uploadService.moveToProjectDir(file);

        return "UPLOADED";
    }
}
