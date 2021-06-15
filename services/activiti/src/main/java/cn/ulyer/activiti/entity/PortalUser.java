package cn.ulyer.activiti.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PortalUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    /**
     * md5字符串
     */
    private String secret;

    private String name;

    private String phone;

    private String remark;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;


}
