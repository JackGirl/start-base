package cn.ulyer.baseserver.api;

import cn.hutool.core.util.StrUtil;
import cn.ulyer.baseserver.service.GatewayLogService;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.model.GateWayLog;
import cn.ulyer.common.utils.PageResult;
import cn.ulyer.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gateway")
public class GatewayLogController {


    @Autowired
    private GatewayLogService gatewayLogService;


    @GetMapping("/list")
    public R<List<GateWayLog>> list(GateWayLog gateWayLog, @RequestParam(SystemConstants.PAGE_NAME)Integer page,@RequestParam(SystemConstants.SIZE_PARAM) Integer size){
        LambdaQueryWrapper<GateWayLog> queryWrapper =  Wrappers.<GateWayLog>lambdaQuery().select(GateWayLog::getId,GateWayLog::getIp,GateWayLog::getMethod,GateWayLog::getPath,GateWayLog::getRequestTime,GateWayLog::getServiceId,
                GateWayLog::getQueryParam,
                GateWayLog::getUserAgent,GateWayLog::getResponseTime,GateWayLog::getResponseStatus).orderByDesc(GateWayLog::getRequestTime);
        if(gateWayLog!=null){
            queryWrapper.eq(StrUtil.isNotBlank(gateWayLog.getPath()),GateWayLog::getPath,gateWayLog.getPath())
                    .eq(StrUtil.isNotBlank(gateWayLog.getServiceId()),GateWayLog::getServiceId,gateWayLog.getServiceId());
        }
        Page<GateWayLog> pager = gatewayLogService.page(new Page<>(page,size),queryWrapper);
        return R.success().setData(new PageResult(pager));
    }


}
