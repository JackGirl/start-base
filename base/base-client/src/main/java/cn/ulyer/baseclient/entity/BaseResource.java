package cn.ulyer.baseclient.entity;

import cn.ulyer.common.model.AbstractBaseModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "path cannot be null")
    private String path;

    @NotBlank(message = "authority cannot be null")
    private String authority;

    private Integer needAuth;

    private Integer isPublic;

    private Integer status;

    @NotBlank(message = "serviceId cannot be null")
    private String serviceId;

    private String resourceName;

    public boolean isPublicApi(){
        return isPublic==1;
    }

    public boolean needAuth(){
        return needAuth==1;
    }


}
