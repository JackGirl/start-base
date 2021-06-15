package cn.ulyer.activiti.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 附件信息
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EepDepositAttach implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String fileName;

    /**
     * 文件信息id
     */
    private String depositId;

    /**
     * 文件存储路径
     */
    private String path;

    /**
     * 文件hash
     */
    private String fileHash;

    /**
     * 签名
     */
    private String sign;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 分片信息存储
     */
    private String sharding;

    /**
     * 创建时间
     */
    private Date createTime;

    private Integer dekv;


}
