package cn.ulyer.baseclient.vo;

import cn.ulyer.baseclient.entity.BaseAction;
import cn.ulyer.baseclient.entity.BaseResource;
import lombok.Data;

import java.util.List;

@Data
public class ActionVo extends BaseAction {

    private List<BaseResource> resources;
}
