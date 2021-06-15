package cn.ulyer.activiti.api;

import cn.ulyer.common.utils.OauthUtil;
import cn.ulyer.common.utils.R;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/process")
public class ProcessApi {


    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngineConfiguration processEngine;

    @Autowired
    private RuntimeService runtimeService;





    @RequestMapping("/{processKey}/start/")
    public R start(@RequestBody(required = false) JSONObject jsonObject, @PathVariable String processKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(processKey, jsonObject, OauthUtil.getClientId());
        return R.success();
    }


}
