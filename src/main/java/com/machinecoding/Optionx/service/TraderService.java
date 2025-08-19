package com.machinecoding.Optionx.service;

import com.machinecoding.Optionx.dto.PnlRecordRequestDTO;
import com.machinecoding.Optionx.dto.TopTraderResponseDTO;
import com.machinecoding.Optionx.dto.TraderDetailsRequestDTO;
import com.machinecoding.Optionx.dto.TraderInsightResponse;

import java.util.Date;
import java.util.List;

public interface TraderService {
    void saveTrader(TraderDetailsRequestDTO trader);

    void traderBulkUpload(List<PnlRecordRequestDTO> pnlRecordRequestDTOS);

    List<TraderInsightResponse>  getTraderInsight(Date startdate, Date enddate);

    List<TopTraderResponseDTO> getTopTraders(Date start, Date end);



}
