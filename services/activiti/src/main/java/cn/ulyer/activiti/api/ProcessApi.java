package cn.ulyer.activiti.api;

import cn.ulyer.common.utils.OauthUtil;
import cn.ulyer.common.utils.R;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
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
    private SpringProcessEngineConfiguration processEngine;

    @Autowired
    private RuntimeService runtimeService;


    @RequestMapping("/create")
    public R<String> create(@RequestParam String modelName, @RequestParam String modelKey) {
        String description = "description";
        JSONObject editorNode = new JSONObject();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        JSONObject stencilSetNode = new JSONObject();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        JSONObject modelObjectNode = new JSONObject();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelName);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        Model modelData = repositoryService.newModel();
        modelData.setMetaInfo(modelObjectNode.toJSONString());
        modelData.setName(modelName);
        modelData.setKey(modelKey);
        //保存模型
        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toJSONString().getBytes(StandardCharsets.UTF_8));
        /**
         * 多租户
         */
        modelData.setTenantId(OauthUtil.getClientId());
        return R.success(modelData.getId());
    }


    @RequestMapping("/${processKey}/start/")
    public R start(@RequestBody(required = false) Map<String, Object> jsonObject, @PathVariable String processKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(processKey, jsonObject, OauthUtil.getClientId());
        return R.success();
    }


}
