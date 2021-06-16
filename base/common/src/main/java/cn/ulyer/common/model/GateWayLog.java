package cn.ulyer.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("gateway_log")
public class GateWayLog {

    private Long id;

    private String path;

    private String serviceId;

    private String ip;

    private String method;

    private String userAgent;

    private Date requestTime;

    private String queryParam;

    private String requestHeaders;

    private String responseStatus;

    private Date responseTime;

    private String responseHeader;



}
