package cn.ulyer.activiti.api;

import cn.hutool.core.lang.Assert;
import cn.ulyer.common.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("/deploy")
public class DeployApi {


    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/active/{modelId}")
    public R activeModel(@PathVariable String modelId, @RequestParam String processName) throws IOException {
        Model model = repositoryService.getModel(modelId);
        Assert.notNull(model);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(objectMapper.readTree(repositoryService.getModelEditorSource(modelId)));
        repositoryService.createDeployment().addBpmnModel(processName,bpmnModel).name(model.getName()).deploy();
        return R.success();
    }



}
