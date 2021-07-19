package cz.marcis.calculations.excelapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Startup {

    @EventListener
    public void printInfo(ApplicationReadyEvent e) {
        log.info("java io temp dir: {}", System.getProperty("java.io.tmpdir"));

        log.warn("App will do not load any initial excel. Needs to be explicitly uploaded.");

        log.debug("App successfully started.");
    }
}
