package cz.marcis.calculations.excelapi.controller;

import cz.marcis.calculations.excelapi.service.SheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DataController {

    private final SheetService service;

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
}
