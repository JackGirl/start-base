package cn.ulyer.activiti.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-06-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActRuEventSubscr implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID_", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("REV_")
    private Integer rev;

    @TableField("EVENT_TYPE_")
    private String eventType;

    @TableField("EVENT_NAME_")
    private String eventName;

    @TableField("EXECUTION_ID_")
    private String executionId;

    @TableField("PROC_INST_ID_")
    private String procInstId;

    @TableField("ACTIVITY_ID_")
    private String activityId;

    @TableField("CONFIGURATION_")
    private String configuration;

    @TableField("CREATED_")
    private Date created;

    @TableField("PROC_DEF_ID_")
    private String procDefId;

    @TableField("TENANT_ID_")
    private String tenantId;


}
