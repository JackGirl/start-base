package cn.ulyer.baseclient.vo;

import lombok.Data;

@Data
public class BaseActionVo {

    private Long actionId;

    private String actionName;

    private String actionValue;

    private String remark;
}
