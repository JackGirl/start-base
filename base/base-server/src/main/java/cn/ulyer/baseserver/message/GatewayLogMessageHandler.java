package cn.ulyer.baseserver.message;


import cn.ulyer.baseserver.service.GatewayLogService;
import cn.ulyer.common.binder.LogInput;
import cn.ulyer.common.model.GateWayLog;
import cn.ulyer.common.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GatewayLogMessageHandler {


    @Autowired
    private GatewayLogService gatewayLogService;


    @StreamListener(value = LogInput.INPUT)
    public void logHandler(GateWayLog gateWayLog){
        try{
            log.info("received one message :{}",gateWayLog);
            gateWayLog.setId(IdWorker.next());
            gatewayLogService.save(gateWayLog);
        }catch (Exception e){
            log.warn("lost one log ：{}",gateWayLog);
        }
    }




}
