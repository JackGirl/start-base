package cn.ulyer.baseapi.entity;

import cn.ulyer.common.model.AbstractBaseModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

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
@TableName("base_resource_server")
public class BaseResourceServer extends AbstractBaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "serviceId", type = IdType.INPUT)
    @NotBlank
    private String serviceId;

    @NotBlank(message = "服务器名不能为空")
    private String serviceName;

    @NotBlank(message = "服务器名不能为空")
    private String routerMatch;

    private Integer status;





}
