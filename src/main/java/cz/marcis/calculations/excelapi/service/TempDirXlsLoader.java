package cz.marcis.calculations.excelapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TempDirXlsLoader {

    private final LoadedExcel loadedExcel;

    public void loadExcelFromTempDirectory() {
        Resource resource = new FileSystemResource(LoadedExcel.EXCEL_DIR + LoadedExcel.EXCEL_FILE_NAME);
        try (FileInputStream fis = new FileInputStream(resource.getFile())) {
            Workbook wb = new HSSFWorkbook(fis);
            Sheet sheet = wb.getSheetAt(0);

            loadedExcel.setSheet(sheet);
            loadedExcel.setWorkbook(wb);

        } catch (FileNotFoundException fileNotFoundException) {
            log.error("file not found", fileNotFoundException);
        } catch (IOException ioException) {
            log.error("IO Exception", ioException);
        }
        log.debug("..excel loaded.");
    }
}
