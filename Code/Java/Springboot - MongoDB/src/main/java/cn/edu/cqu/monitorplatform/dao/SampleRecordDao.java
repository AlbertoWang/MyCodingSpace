package cn.edu.cqu.monitorplatform.dao;

import cn.edu.cqu.monitorplatform.pojo.SampleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SampleRecordDao extends MongoRepository<SampleRecord, String> {
}
