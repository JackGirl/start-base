package cn.ulyer.activiti.api;

import cn.hutool.core.util.StrUtil;
import cn.ulyer.activiti.entity.ActReModel;
import cn.ulyer.activiti.service.ActReModelService;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.utils.OauthUtil;
import cn.ulyer.common.utils.PageResult;
import cn.ulyer.common.utils.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_DESCRIPTION;
import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_NAME;

@RestController
@Slf4j
public class ModelApi {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper      objectMapper;

    @Autowired
    private ActReModelService actReModelService;


    @GetMapping("/list")
    public R<PageResult<Model>> list(@RequestParam(required = false) String modelName, @RequestParam(required = false) String modelKey
      ,@RequestParam(SystemConstants.PAGE_NAME)Integer page,@RequestParam(SystemConstants.SIZE_PARAM) Integer limit){
        Page<ActReModel> model = actReModelService.page(new Page<>(page,limit),
                Wrappers.<ActReModel>lambdaQuery()
                        .eq(StrUtil.isNotBlank(modelKey),ActReModel::getKey,modelKey)
                        .eq(ActReModel::getTenantId,OauthUtil.getClientId())
                        .like(StrUtil.isNotBlank(modelName),ActReModel::getName,modelName));
        return R.success().setData(new PageResult<>(model));
    }



    @RequestMapping(
            value = {"/model/{modelId}/json"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public ObjectNode getEditorJson(@PathVariable String modelId) {
        ObjectNode modelNode = null;
        Model model = this.repositoryService.getModel(modelId);
        if (model != null) {
            try {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode)this.objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = this.objectMapper.createObjectNode();
                    modelNode.put("name", model.getName());
                }
                ObjectNode stencilSetNode = objectMapper.createObjectNode();
                stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
                modelNode.set("stencilset", stencilSetNode);
                modelNode.put("modelId", model.getId());
                ObjectNode editorJsonNode = (ObjectNode)this.objectMapper.readTree(new String(this.repositoryService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.set("model", editorJsonNode);
            } catch (Exception var5) {
                log.error("Error creating model JSON", var5);
                throw new ActivitiException("Error creating model JSON", var5);
            }
        }

        return modelNode;
    }




    @RequestMapping(
            value = {"/model/{modelId}/save"},
            method = {RequestMethod.PUT}
    )
    @ResponseStatus(HttpStatus.OK)
    public void saveModel(@PathVariable String modelId,@RequestParam String name,@RequestParam String description,
                          @RequestParam String json_xml,@RequestParam String svg_xml) {
        try {
            Model model = this.repositoryService.getModel(modelId);
            JSONObject modelJson = JSON.parseObject( model.getMetaInfo());
            modelJson.put(MODEL_NAME,name);
            modelJson.put(MODEL_DESCRIPTION,description);
            model.setMetaInfo(modelJson.toJSONString());
            model.setName(name);
            this.repositoryService.saveModel(model);
            this.repositoryService.addModelEditorSource(model.getId(), (json_xml.getBytes(StandardCharsets.UTF_8)));
            InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes(StandardCharsets.UTF_8));
            TranscoderInput input = new TranscoderInput(svgStream);
            PNGTranscoder transcoder = new PNGTranscoder();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);
            transcoder.transcode(input, output);
            byte[] result = outStream.toByteArray();
            this.repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();
        } catch (Exception var11) {
            log.error("Error saving model", var11);
            throw new ActivitiException("Error saving model", var11);
        }
    }


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
        modelData.setTenantId(OauthUtil.getClientId());
        //保存模型
        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toJSONString().getBytes(StandardCharsets.UTF_8));
        /**
         * 多租户
         */
        modelData.setTenantId(OauthUtil.getClientId());
        return R.success(modelData.getId());
    }
}
