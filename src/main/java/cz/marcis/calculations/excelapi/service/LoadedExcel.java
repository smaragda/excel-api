package cz.marcis.calculations.excelapi.service;

import lombok.Data;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

/**
 * Singleton
 * - only one sheet of Excel is supported only.
 * - only one sheet of Excel is supported only.
 */
@Data
@Component
class LoadedExcel {
    static final String EXCEL_DIR = System.getProperty("java.io.tmpdir");
    static final String EXCEL_FILE_NAME = "to_be_evaluated.xls";

    private Workbook workbook;
    private Sheet sheet;
}
