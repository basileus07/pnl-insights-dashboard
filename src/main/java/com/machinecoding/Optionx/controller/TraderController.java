package com.machinecoding.Optionx.controller;

import com.machinecoding.Optionx.dto.PnlRecordRequestDTO;
import com.machinecoding.Optionx.dto.TopTraderResponseDTO;
import com.machinecoding.Optionx.dto.TraderDetailsRequestDTO;
import com.machinecoding.Optionx.dto.TraderInsightResponse;
import com.machinecoding.Optionx.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trader")
public class TraderController {

    @Autowired
    private TraderService traderService;

    @PostMapping("/add")
    public ResponseEntity<String> onboardTrader(@RequestBody TraderDetailsRequestDTO requestDTO) {
        traderService.saveTrader(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Trader onboarded successfully");
    }

    @PostMapping("/pnl/bulk")
    public ResponseEntity<String> bulkUpload(@RequestBody List<PnlRecordRequestDTO> pnlRecordRequestDTOS) {
        traderService.traderBulkUpload(pnlRecordRequestDTOS);
        return ResponseEntity.status(HttpStatus.CREATED).body("PnL records uploaded successfully");
    }


    @GetMapping("/insight")
    public ResponseEntity<List<TraderInsightResponse>> getTraderInsight(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate) {

        // Convert input dates to UTC
        LocalDate startLocal = startdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocal = enddate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Date startUTC = Date.from(startLocal.atStartOfDay(ZoneOffset.UTC).toInstant());
        Date endUTC = Date.from(endLocal.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant());


        List<TraderInsightResponse> traderInsightResponses = traderService.getTraderInsight(startUTC, endUTC);
        return ResponseEntity.ok(traderInsightResponses);
    }

    @GetMapping("/top-traders")
    public ResponseEntity<List<TopTraderResponseDTO>> getTopTraders(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate) {

        // Convert to UTC dates (same as your existing insight API)
        LocalDate startLocal = startdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocal = enddate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Date start = Date.from(startLocal.atStartOfDay(ZoneOffset.UTC).toInstant());
        Date end = Date.from(endLocal.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant());


        List<TopTraderResponseDTO> topTraders = traderService.getTopTraders(start, end);

        return ResponseEntity.ok(topTraders);
    }

}
