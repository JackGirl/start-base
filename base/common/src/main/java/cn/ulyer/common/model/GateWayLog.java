package cn.ulyer.common.model;

import lombok.*;

import java.util.Date;

@Builder
@Data
public class GateWayLog {

    private String path;

    /**
     * 服务实例 applicationName+serverPort
     */
    private String instance;

    private String ip;

    private String method;

    private String params;

    private String userAgent;

    private Date requestTime;

    private String requestHeaders;

    private String requestBody;

    private String responseStatus;

    private Date responseTime;

    private String responseHeader;

    private String responseBody;

}
