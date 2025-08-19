package com.machinecoding.Optionx.repository.Imp;

import com.machinecoding.Optionx.dto.PnlRecordRequestDTO;
import com.machinecoding.Optionx.pojo.PnlRecordPOJO;
import com.machinecoding.Optionx.pojo.TraderPOJO;
import com.machinecoding.Optionx.repository.TraderRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class TraderRepositoryImp implements TraderRepository {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void saveTrader(TraderPOJO trader) {
        mongoTemplate.insert(trader);
    }

    @Override
    public void bulkUpload(List<PnlRecordRequestDTO> pnlRecordRequestDTOS) {
        mongoTemplate.insert(pnlRecordRequestDTOS, "pnl_records");
    }
    @Override
    public List<PnlRecordPOJO> getAllTraderRecord(Date startDate, Date endDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("traderDate").gte(startDate).lt(endDate));
        return mongoTemplate.find(query, PnlRecordPOJO.class);
    }

    @Override
    public List<PnlRecordPOJO> getTopTraderRecords(Date startDate, Date endDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("traderDate").gte(startDate).lt(endDate));

        String collectionName = mongoTemplate.getCollectionName(PnlRecordPOJO.class);
        List<Document> rawResults = mongoTemplate.find(query, Document.class, collectionName);

        List<PnlRecordPOJO> results = new ArrayList<>();
        for (Document doc : rawResults) {
            try {
                PnlRecordPOJO pojo = convertDocumentToPojo(doc);
                results.add(pojo);
            } catch (Exception e) {
                System.err.println("Error converting document: " + e.getMessage());
            }
        }

        return results;
    }

    private PnlRecordPOJO convertDocumentToPojo(Document doc) {
        PnlRecordPOJO pojo = new PnlRecordPOJO();
        pojo.setId(doc.getObjectId("_id").toString());
        pojo.setTraderId(doc.getString("traderId"));
        pojo.setTraderDate(doc.getDate("traderDate"));
        pojo.setPnlAmount(new BigDecimal(doc.getString("pnlAmount")));
        pojo.setAllocatedMargin(new BigDecimal(doc.getString("allocatedMargin")));
        pojo.setUtilizedMargin(new BigDecimal(doc.getString("utilizedMargin")));
        return pojo;
    }




}
