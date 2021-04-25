package cn.ulyer.baseclient.entity;

import cn.ulyer.common.model.AbstractBaseModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

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
@TableName("base_menu")
public class BaseMenu extends AbstractBaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menuId", type = IdType.ASSIGN_ID)
    private Long menuId;

    private Long parentId;

    private String menuName;

    private String path;

    private String component;

    private Integer status;

    private Integer priority;

    private String icon;

    private String target;

    private String redirect;

    @TableField(exist = false)
    private List<BaseMenu> children;

}
