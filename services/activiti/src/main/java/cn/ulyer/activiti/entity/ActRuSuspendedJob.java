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
public class ActRuSuspendedJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID_", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("REV_")
    private Integer rev;

    @TableField("TYPE_")
    private String type;

    @TableField("EXCLUSIVE_")
    private Boolean exclusive;

    @TableField("EXECUTION_ID_")
    private String executionId;

    @TableField("PROCESS_INSTANCE_ID_")
    private String processInstanceId;

    @TableField("PROC_DEF_ID_")
    private String procDefId;

    @TableField("RETRIES_")
    private Integer retries;

    @TableField("EXCEPTION_STACK_ID_")
    private String exceptionStackId;

    @TableField("EXCEPTION_MSG_")
    private String exceptionMsg;

    @TableField("DUEDATE_")
    private Date duedate;

    @TableField("REPEAT_")
    private String repeat;

    @TableField("HANDLER_TYPE_")
    private String handlerType;

    @TableField("HANDLER_CFG_")
    private String handlerCfg;

    @TableField("TENANT_ID_")
    private String tenantId;


}
