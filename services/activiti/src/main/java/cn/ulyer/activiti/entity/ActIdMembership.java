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
public class ActIdMembership implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "USER_ID_", type = IdType.ASSIGN_ID)
    private String userId;

    @TableField("GROUP_ID_")
    private String groupId;


}
