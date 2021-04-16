package cn.ulyer.baseclient.entity;

import cn.ulyer.common.model.AbstractBaseModel;
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
@TableName("base_role_resource")
public class BaseRoleResource extends AbstractBaseModel {

    private static final long serialVersionUID = 1L;

    private Long resourceId;

    private Long roleId;

    private Date createTime;

    private Date updateTime;


}
