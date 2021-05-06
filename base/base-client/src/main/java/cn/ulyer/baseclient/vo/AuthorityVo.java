package cn.ulyer.baseclient.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class AuthorityVo extends ResourceVo{

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date expireTime;

    private Long appResourceId;

}
