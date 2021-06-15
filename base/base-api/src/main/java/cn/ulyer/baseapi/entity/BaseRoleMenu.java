package cn.ulyer.baseapi.entity;

import cn.ulyer.common.model.AbstractBaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("base_role_menu")
public class BaseRoleMenu extends AbstractBaseModel {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;



}
