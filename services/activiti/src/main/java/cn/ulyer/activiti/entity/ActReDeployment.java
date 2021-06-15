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
public class ActReDeployment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID_", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("NAME_")
    private String name;

    @TableField("CATEGORY_")
    private String category;

    @TableField("KEY_")
    private String key;

    @TableField("TENANT_ID_")
    private String tenantId;

    @TableField("DEPLOY_TIME_")
    private Date deployTime;

    @TableField("ENGINE_VERSION_")
    private String engineVersion;


}
