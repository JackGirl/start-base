package cn.ulyer.baseclient.entity;

import cn.ulyer.common.model.AbstractBaseModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_action")
public class BaseAction extends AbstractBaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "actionId", type = IdType.ASSIGN_ID)
    private Long actionId;

    private String actionName;

    private String actionValue;

    private Boolean status;

    private Integer priority;

    private String remark;

    private Long menuId;



}
