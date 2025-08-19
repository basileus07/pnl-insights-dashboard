package com.machinecoding.Optionx.repository;

import com.machinecoding.Optionx.dto.PnlRecordRequestDTO;
import com.machinecoding.Optionx.pojo.PnlRecordPOJO;
import com.machinecoding.Optionx.pojo.TraderPOJO;
import lombok.Data;

import java.util.Date;
import java.util.List;

public interface TraderRepository {
    void saveTrader(TraderPOJO traderPOJO);

    void bulkUpload(List<PnlRecordRequestDTO> pnlRecordRequestDTOS);

    List<PnlRecordPOJO> getAllTraderRecord(Date startdate, Date enddate);

    List<PnlRecordPOJO>getTopTraderRecords(Date startDate, Date endDate);
}

