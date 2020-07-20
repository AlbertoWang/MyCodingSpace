package cn.edu.cqu.monitorplatform.controller;

import cn.edu.cqu.monitorplatform.pojo.SampleRecord;
import cn.edu.cqu.monitorplatform.service.SampleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SampleController
 * @Description TODO
 * @Author AlbertoWang
 * @Date 02:27
 **/

@RestController
public class SampleController {

    @Resource
    private SampleService sampleService;

    @GetMapping("/sampleRecords")
    @ResponseBody
    public List<SampleRecord> getSampleRecords(@RequestParam(name = "recordId", required = false) String recordId, @RequestParam(name = "recordType", required = false) String recordType, @RequestParam(name = "recordTime", required = false) Date recordTime, @RequestParam(name = "recordSampler", required = false) String recordSampler) {
        return sampleService.getSampleRecords(recordId, recordType, recordTime, recordSampler);
    }

    @PostMapping("/newSampleRecord")
    @ResponseBody
    public boolean insertSampleRecord(@RequestBody SampleRecord sampleRecord) {
        return sampleService.insertSampleRecord(sampleRecord);
    }

    @PostMapping("/modifiedSampleRecord")
    @ResponseBody
    public long updateSampleRecord(@RequestBody SampleRecord sampleRecord) {
        return sampleService.updateSampleRecord(sampleRecord);
    }

    @PostMapping("/deprecatorySampleRecord")
    @ResponseBody
    public boolean deleteSampleRecords(@RequestParam(name = "recordIds", required = true) List<String> recordIds) {
        return sampleService.deleteSampleRecords(recordIds);
    }

}
