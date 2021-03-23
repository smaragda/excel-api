package cz.marcis.calculations.excelapi;

import lombok.Data;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

/**
 * One sheet of excel is supported only.
 */
@Data
@Component
public class LoadedExcel {
    private Workbook workbook;
    private Sheet sheet;
}
