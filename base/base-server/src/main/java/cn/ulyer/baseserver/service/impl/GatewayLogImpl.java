package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseserver.mapper.GatewayLogMapper;
import cn.ulyer.baseserver.service.GatewayLogService;
import cn.ulyer.common.model.GateWayLog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GatewayLogImpl extends ServiceImpl<GatewayLogMapper, GateWayLog> implements GatewayLogService {
}
