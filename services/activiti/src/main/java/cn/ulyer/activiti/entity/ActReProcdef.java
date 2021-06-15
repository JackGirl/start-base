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
public class ActReProcdef implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID_", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("REV_")
    private Integer rev;

    @TableField("CATEGORY_")
    private String category;

    @TableField("NAME_")
    private String name;

    @TableField("KEY_")
    private String key;

    @TableField("VERSION_")
    private Integer version;

    @TableField("DEPLOYMENT_ID_")
    private String deploymentId;

    @TableField("RESOURCE_NAME_")
    private String resourceName;

    @TableField("DGRM_RESOURCE_NAME_")
    private String dgrmResourceName;

    @TableField("DESCRIPTION_")
    private String description;

    @TableField("HAS_START_FORM_KEY_")
    private Integer hasStartFormKey;

    @TableField("HAS_GRAPHICAL_NOTATION_")
    private Integer hasGraphicalNotation;

    @TableField("SUSPENSION_STATE_")
    private Integer suspensionState;

    @TableField("TENANT_ID_")
    private String tenantId;

    @TableField("ENGINE_VERSION_")
    private String engineVersion;


}
