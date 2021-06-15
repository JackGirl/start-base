package cn.ulyer.activiti.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 原生存证信息
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EepDepositdata implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 编号
     */
    private String depositCode;

    /**
     * 接入方业务id
     */
    private String accessBizId;

    /**
     * 存证用户名称
     */
    private String userName;

    /**
     * 存证内容
     */
    private String content;

    /**
     * 客户端编号
     */
    private String clientCode;

    /**
     * 存证时间戳
     */
    private Date sysTime;

    /**
     * 时间戳签名
     */
    private String sysTimeSign;

    /**
     * 存证来源
     */
    private String certSource;

    /**
     * 来源ip
     */
    private String sourceIp;

    /**
     * 目标ip
     */
    private String targetIp;

    /**
     * 服务器地址
     */
    private String serviceAddr;

    /**
     * 授时中心保全时间
     */
    private Date authServerSavetime;

    /**
     * 签名证书公钥
     */
    private String customSignCert;

    /**
     * 用户签名摘要
     */
    private String hash;

    /**
     * 用户签名附件摘要
     */
    private String attachHash;

    /**
     * 用户签名附件摘要签名
     */
    private String customAttachSign;

    /**
     * 用户签名
     */
    private String customSign;

    /**
     * 系统签名证书公钥
     */
    private String sysSignPubkey;

    /**
     * 系统签名摘要
     */
    private String sysAttachSign;

    /**
     * 系统签名
     */
    private String sysSign;

    /**
     * 制卡状态  默认0：未生成电子凭证   1：已生成
     */
    private Integer status;

    /**
     * 标签  多值用，分割
     */
    private String tag;

    /**
     * 描述
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField("dekV")
    private Integer dekv;


}
