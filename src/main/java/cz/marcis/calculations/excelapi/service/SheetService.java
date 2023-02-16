package cz.marcis.calculations.excelapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SheetService {

    private final ObjectMapper objectMapper;
    private final LoadedExcel loadedExcel;

    @SneakyThrows
    public Map<String, Number> evaluateSheet(String inputCellsSheet) {
        Map<String, Object> inputCells = objectMapper.readValue(inputCellsSheet, HashMap.class);

        replaceValues(inputCells);
        evaluate();

        return readSheetAsMap();
    }

    private Map<String, Number> readSheetAsMap() {
        val map = new HashMap<String, Number>();

        for (Row r : loadedExcel.getSheet()) {
            for (Cell c : r) {
                getDoubleValue(c).ifPresent(aDouble -> mapOnlyNumbers(map, c, aDouble));
            }
        }
        return map;
    }

    private void mapOnlyNumbers(HashMap<String, Number> map, Cell c, Double aDouble) {
        CellReference cellReference = new CellReference(c.getRowIndex(), c.getColumnIndex());
        String ref = cellReference.formatAsString();
        map.put(ref, aDouble);
    }

    private Optional<Double> getDoubleValue(Cell c) {
        try {
            return Optional.of(c.getNumericCellValue());
        } catch (IllegalStateException e) {
            return Optional.empty();
        }
    }

    private void replaceValues(Map<String, Object> inputCells) {
        inputCells.forEach(this::replaceValue);
    }

    private void replaceValue(String cellName, Object value) {
        double dValue = 0d;
        if (value instanceof String) {
            dValue = Double.parseDouble((String) value);
        }
        if (value instanceof Double) {
            dValue = (Double) value;
        }
        if (value instanceof Integer) {
            dValue = ((Integer) value).doubleValue();
        }

        val cellReference = new CellReference(cellName);
        val row = loadedExcel.getSheet().getRow(cellReference.getRow());
        val cell = row.getCell(cellReference.getCol());

        log.debug("replacing value '{}' in cell {} by value '{}'!", cell.getNumericCellValue(), cellName, value);
//        cell.setCellValue(value.doubleValue());
        cell.setCellValue(dValue);
    }

    public void evaluate() {
        FormulaEvaluator evaluator = loadedExcel.getWorkbook().getCreationHelper().createFormulaEvaluator();

        for (Row r : loadedExcel.getSheet()) {
            for (Cell c : r) {
                if (c.getCellType() == CellType.FORMULA) {
                    evaluator.evaluateFormulaCell(c);
                }
            }
        }
    }
}
