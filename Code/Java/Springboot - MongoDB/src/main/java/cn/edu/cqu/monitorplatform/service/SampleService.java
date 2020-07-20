package cn.edu.cqu.monitorplatform.service;

import cn.edu.cqu.monitorplatform.pojo.SampleRecord;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SampleService
 * @Description TODO
 * @Author AlbertoWang
 * @Date 02:47
 **/

@Service
public interface SampleService {

    // 获取采样记录，根据输入进行筛选，若无输入则返回全部记录
    List<SampleRecord> getSampleRecords(String recordId, String recordType, Date recordTime, String recordSampler);

    // 插入新采样记录，返回操作结果
    boolean insertSampleRecord(SampleRecord sampleRecord);

    // 更新采样记录，返回修改记录个数
    long updateSampleRecord(SampleRecord sampleRecord);

    // 按ID删除采样记录，返回操作结果
    boolean deleteSampleRecords(List<String> recordIds);
}
