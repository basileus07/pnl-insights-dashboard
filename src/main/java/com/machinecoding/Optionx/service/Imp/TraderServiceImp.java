package com.machinecoding.Optionx.service.Imp;

import com.machinecoding.Optionx.dto.PnlRecordRequestDTO;
import com.machinecoding.Optionx.dto.TopTraderResponseDTO;
import com.machinecoding.Optionx.dto.TraderDetailsRequestDTO;
import com.machinecoding.Optionx.dto.TraderInsightResponse;
import com.machinecoding.Optionx.pojo.PnlRecordPOJO;
import com.machinecoding.Optionx.pojo.TopTraderResponsePOJO;
import com.machinecoding.Optionx.pojo.TraderPOJO;
import com.machinecoding.Optionx.repository.TraderRepository;
import com.machinecoding.Optionx.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TraderServiceImp implements TraderService {

    @Autowired
    private TraderRepository traderRepository;
    @Override
    public void saveTrader(TraderDetailsRequestDTO trader) {
        TraderPOJO traderPOJO = new TraderPOJO();
        traderPOJO.setTraderId(UUID.randomUUID().toString());
        traderPOJO.setName(trader.getName());
        traderPOJO.setDate(new Date());

        traderRepository.saveTrader(traderPOJO);
    }

    @Override
    public void traderBulkUpload(List<PnlRecordRequestDTO> pnlRecordRequestDTOS) {
        List<PnlRecordPOJO> pnlRecords = pnlRecordRequestDTOS.stream().map(dto -> {
            PnlRecordPOJO pojo = new PnlRecordPOJO();
            pojo.setPnlAmount(dto.getPnlAmount());          // BigDecimal
            pojo.setTraderId(dto.getTraderId());            // String
            pojo.setAllocatedMargin(dto.getAllocatedMargin());
            pojo.setUtilizedMargin(dto.getUtilizedMargin());
            pojo.setTraderDate(dto.getTraderDate());          // Date
            return pojo;
        }).collect(Collectors.toList());
       traderRepository.bulkUpload(pnlRecordRequestDTOS);
    }

    @Override
    public List<TraderInsightResponse> getTraderInsight(Date startDate, Date endDate) {
        List<PnlRecordPOJO> records = traderRepository.getAllTraderRecord(startDate, endDate);

        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }

        // Group records by tradeDate
        Map<Date, List<PnlRecordPOJO>> recordsByDate = records.stream()
                .collect(Collectors.groupingBy(PnlRecordPOJO::getTraderDate));

        // For each date, calculate avg, max, min pnl%
        List<TraderInsightResponse> insights = new ArrayList<>();

        for (Map.Entry<Date, List<PnlRecordPOJO>> entry : recordsByDate.entrySet()) {
            Date tradeDate = entry.getKey();
            List<PnlRecordPOJO> dailyRecords = entry.getValue();

            DoubleSummaryStatistics stats = dailyRecords.stream()
                    .map(record -> {
                        if (record.getAllocatedMargin() == null || record.getAllocatedMargin().compareTo(BigDecimal.ZERO) == 0) {
                            return 0.0; // avoid divide by zero
                        }
                        return record.getPnlAmount()
                                .divide(record.getAllocatedMargin(), 6, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100))
                                .doubleValue();
                    })
                    .mapToDouble(Double::doubleValue)
                    .summaryStatistics();

            TraderInsightResponse response = new TraderInsightResponse();
            response.setTradingdate(tradeDate);
            response.setAvgPnlPercentage(BigDecimal.valueOf(stats.getAverage()));
            response.setMaxPnlPercentage(BigDecimal.valueOf(stats.getMax()));
            response.setMinPnlPercentage(BigDecimal.valueOf(stats.getMin()));

            insights.add(response);
        }

        // Sort by date before returning
        insights.sort(Comparator.comparing(TraderInsightResponse::getTradingdate));

        return insights;
    }

    @Override
    public List<TopTraderResponseDTO> getTopTraders(Date startDate, Date endDate) {
        List<PnlRecordPOJO> records = traderRepository.getTopTraderRecords(startDate, endDate);

        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }

        // Group records by traderId
        Map<String, List<PnlRecordPOJO>> recordsByTrader = records.stream()
                .collect(Collectors.groupingBy(PnlRecordPOJO::getTraderId));

        // Calculate average PnL percentage for each trader
        List<TopTraderResponseDTO> traderStats = new ArrayList<>();


        for (Map.Entry<String, List<PnlRecordPOJO>> entry : recordsByTrader.entrySet()) {
            String traderId = entry.getKey();
            List<PnlRecordPOJO> traderRecords = entry.getValue();

            // Calculate PnL percentages for all trades of this trader
            List<Double> pnlPercentages = traderRecords.stream()
                    .map(record -> {
                        if (record.getAllocatedMargin() == null ||
                                record.getAllocatedMargin().compareTo(BigDecimal.ZERO) == 0) {
                            return 0.0;
                        }
                        return record.getPnlAmount()
                                .divide(record.getAllocatedMargin(), 6, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100))
                                .doubleValue();
                    })
                    .collect(Collectors.toList());

            // Calculate average PnL percentage
            double averagePnlPercentage = pnlPercentages.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            // Calculate total PnL amount
            BigDecimal totalPnlAmount = traderRecords.stream()
                    .map(PnlRecordPOJO::getPnlAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Create response object
            TopTraderResponseDTO traderResponse = new TopTraderResponseDTO(
                    traderId,
                    BigDecimal.valueOf(averagePnlPercentage).setScale(2, RoundingMode.HALF_UP),
                    traderRecords.size(),
                    totalPnlAmount
            );

            traderStats.add(traderResponse);
        }

        // Sort by average PnL percentage (descending) and return top 3
        return traderStats.stream()
                .sorted((t1, t2) -> t2.getAveragePnlPercentage().compareTo(t1.getAveragePnlPercentage()))
                .limit(3)
                .collect(Collectors.toList());
    }


}
