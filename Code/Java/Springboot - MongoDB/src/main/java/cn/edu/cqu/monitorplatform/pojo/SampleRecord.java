package cn.edu.cqu.monitorplatform.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.HashMap;

/**
 * @ClassName SampleRecord
 * @Description Model of sample_record
 * @Author AlbertoWang
 * @Date 02:30
 **/

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("sample_record")
public class SampleRecord {

    @Id
    private String id;
    @Field("record_id")
    private String recordId;
    @Field("record_type")
    private String recordType;
    @Field("record_details")
    private HashMap<String, Object> recordDetails;
    @Field("record_time")
    private Date recordTime;
    @Field("record_sampler")
    private String recordSampler;
}
