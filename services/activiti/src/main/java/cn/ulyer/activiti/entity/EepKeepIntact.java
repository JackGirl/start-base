package cn.ulyer.activiti.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 数据保全单信息
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EepKeepIntact implements Serializable {

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
     * 存证ID
     */
    private String depositId;

    /**
     * 授时中心保全时间
     */
    private Date authServerSavetime;

    /**
     * 系统时间戳签名
     */
    private String sysTimeSign;

    /**
     * 系统存证时间
     */
    private Date sysTime;

    /**
     * 存证时间
     */
    private Date depositTime;

    /**
     * 接入方业务id
     */
    private String accessBizId;

    /**
     * 客户端编号
     */
    private String clientCode;

    /**
     * 签名摘要
     */
    private String hash;

    /**
     * 用户签名附件摘要
     */
    private String attachHash;

    /**
     * 用户签名
     */
    private String customSign;

    /**
     * 签名证书
     */
    private String customSignCert;

    /**
     * 用户签名附件摘要
     */
    private String customAttachSign;

    /**
     * 系统签名证书公钥
     */
    private String sysSignPubkey;

    /**
     * 系统签名
     */
    private String sysSign;

    /**
     * 系统附件签名
     */
    private String sysAttachSign;

    /**
     * 公证签名
     */
    private String notarySign;

    /**
     * 公证时间戳签名
     */
    private String notaryTimeSign;

    /**
     * 公证处签名时间
     */
    private Date notaryTime;

    /**
     * 凭证编号
     */
    private String certNumber;

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
     * 描述
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 标签  多值用，分割
     */
    private String tag;

    /**
     * 保全单审核状态  默认0：未审核   1：已审核
     */
    private Integer status;

    /**
     * 存证用户名称
     */
    private String userName;


}
