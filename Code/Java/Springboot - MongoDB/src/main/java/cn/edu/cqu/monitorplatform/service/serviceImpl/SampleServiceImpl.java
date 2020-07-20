package cn.edu.cqu.monitorplatform.service.serviceImpl;

import cn.edu.cqu.monitorplatform.pojo.SampleRecord;
import cn.edu.cqu.monitorplatform.service.SampleService;
import cn.edu.cqu.monitorplatform.utils.ClassUtils;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName SampleServiceImpl
 * @Description TODO
 * @Author AlbertoWang
 * @Date 02:49
 **/

@Component
public class SampleServiceImpl implements SampleService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<SampleRecord> getSampleRecords(String recordId, String recordType, Date recordTime, String recordSampler) {
        Query query = new Query(Criteria.where("id").ne(""));
        if (recordId != null) {
            query.addCriteria(Criteria.where("record_id").is(recordId));
        }
        if (recordType != null) {
            query.addCriteria(Criteria.where("record_type").is(recordType));
        }
        if (recordTime != null) {
            query.addCriteria(Criteria.where("record_time").is(recordTime));
        }
        if (recordSampler != null) {
            query.addCriteria(Criteria.where("record_sampler").is(recordSampler));
        }
        return mongoTemplate.find(query, SampleRecord.class, "sample_record");
    }

    @Override
    public boolean insertSampleRecord(SampleRecord sampleRecord) {
        try {
            mongoTemplate.save(sampleRecord, "sample_record");
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    @Override
    public long updateSampleRecord(SampleRecord sampleRecord) {
        Query query = new Query(Criteria.where("id").is(sampleRecord.getId()));
        Update update = new Update();
        try {
            HashMap<String, Object> variableMap = ClassUtils.classTraversal(sampleRecord);
            for (String variableName : variableMap.keySet()) {
                if (variableMap.get(variableName) != null) {
                    // 非空项进行修改
                    update.set(variableName, variableMap.get(variableName));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, SampleRecord.class, "sample_record");
        if (updateResult != null) {
            return updateResult.getModifiedCount();
        }
        return 0;
    }

    @Override
    public boolean deleteSampleRecords(List<String> recordIds) {
        for (String recordId : recordIds) {
            Query query = new Query(Criteria.where("record_id").is(recordId));
            try {
                mongoTemplate.remove(query, SampleRecord.class, "sample_record");
            } catch (Exception e) {
                System.err.println(e);
                return false;
            }
        }
        return true;
    }

}
