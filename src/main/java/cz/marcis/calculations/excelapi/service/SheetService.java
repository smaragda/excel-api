package cz.marcis.calculations.excelapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.marcis.calculations.excelapi.LoadedExcel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SheetService {

    private final ObjectMapper objectMapper;
    private final LoadedExcel excel;

    @SneakyThrows
    public Map<String, Number> evaluateSheet(String inputCellsSheet) {
        Map<String, Number> inputCells = objectMapper.readValue(inputCellsSheet, HashMap.class);

        replaceValues(inputCells);
        evaluate();

        return readSheetAsMap();

//        return objectMapper.writeValueAsString(readSheetAsMap());
    }

    private Map<String, Number> readSheetAsMap() {
        Map<String, Number> map = new HashMap<>();

        for (Row r : excel.getSheet()) {
            for (Cell c : r) {
                double d = c.getNumericCellValue();
                CellReference cellReference = new CellReference(c.getRowIndex(), c.getColumnIndex());
                String ref = cellReference.formatAsString();

                map.put(ref, d);
            }
        }
        return map;
    }

    private void replaceValues(Map<String, Number> inputCells) {
        inputCells.forEach(this::replaceValue);
    }

    private void replaceValue(String cellName, Number value) {
        CellReference cellReference = new CellReference(cellName);
        Row row = excel.getSheet().getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol());

        log.debug("replacing value '{}' in cell {} by value '{}'!", cell.getNumericCellValue(), cellName, value);
        cell.setCellValue(value.doubleValue());
    }

    public void evaluate() {
        Workbook wb = excel.getWorkbook();
        Sheet sheet = excel.getSheet();

        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

        for (Row r : sheet) {
            for (Cell c : r) {
                if (c.getCellType() == CellType.FORMULA) {
                    evaluator.evaluateFormulaCell(c);
                }
            }
        }
    }


}
