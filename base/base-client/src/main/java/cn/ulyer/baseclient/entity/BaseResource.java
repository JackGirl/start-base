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
@TableName("base_resource")
public class BaseResource extends AbstractBaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "resourceId", type = IdType.ASSIGN_ID)
    private Long resourceId;

    private String path;

    private String auhtority;

    private Boolean needAuth;

    private Boolean isPublic;

    private Integer status;

    private String serviceId;

    private String resourceName;

    private Date createTime;

    private Date updateTime;


}
