package cn.ulyer.activiti.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class ActRuIdentitylink implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID_", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("REV_")
    private Integer rev;

    @TableField("GROUP_ID_")
    private String groupId;

    @TableField("TYPE_")
    private String type;

    @TableField("USER_ID_")
    private String userId;

    @TableField("TASK_ID_")
    private String taskId;

    @TableField("PROC_INST_ID_")
    private String procInstId;

    @TableField("PROC_DEF_ID_")
    private String procDefId;


}
