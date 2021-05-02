package cn.ulyer.baseclient.entity;

import cn.ulyer.common.model.AbstractBaseModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "菜单名不能为空")
    private String menuName;

    @NotBlank(message = "菜单路径不能为空")
    private String path;

    @NotBlank(message = "菜单组建不能为空")
    private String component;

    private Integer status;

    private Integer priority;

    private String icon;

    private String target;

    private String redirect;


}
