package cz.marcis.calculations.excelapi.controller;

import cz.marcis.calculations.excelapi.service.SheetService;
import cz.marcis.calculations.excelapi.service.TempDirXlsLoader;
import cz.marcis.calculations.excelapi.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class DataController {

    private final SheetService service;
    private final UploadService uploadService;
    private final TempDirXlsLoader loader;

    /**
     * Gets calculated sheet result with evaluated cells.
     *
     * @param inputCellsSheet sheet with populated data values - NOT with formulas, just values.
     * @return calculated sheet result - evaluated cells
     */
    @PostMapping(value = "/calc")
    public Map<String, Number> getCalculatedSheet(@RequestBody String inputCellsSheet) {
        log.info("Request for evaluating excel received.");
        return service.evaluateSheet(inputCellsSheet);
    }

    @PostMapping(value = "/upload", produces = "plain/text")
    public String submit(@RequestParam("file") MultipartFile file) {
        log.info("Request for uploading excel received.");

        uploadService.moveToProjectDir(file);
        loader.loadExcelFromTempDirectory();

        return "UPLOADED";
    }
}
