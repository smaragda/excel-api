package cz.marcis.calculations.excelapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Startup {

    private final LoadedExcel loadedExcel;


    @EventListener
    public void loadExcel(ApplicationReadyEvent e) {
        log.info("loading excel..");
        Resource resource = new ClassPathResource("easy.xls");
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
        log.info("..excel loaded.");
    }

}
