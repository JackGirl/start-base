package cn.ulyer.common.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter

/**
 * fill in {@link cn.ulyer.common.config.MybatisPlusConfig.Audit}
 */
public abstract class AbstractBaseModel implements Serializable {

    @JsonFormat(timezone = "GMT+8",pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8",pattern = "YYYY-MM-dd HH:mm:ss")
    private Date updateTime;


}
