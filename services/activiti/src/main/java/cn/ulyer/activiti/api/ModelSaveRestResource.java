package cn.ulyer.activiti.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_DESCRIPTION;
import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_NAME;

@RestController
@Slf4j
public class ModelSaveRestResource {

    @Autowired
    private RepositoryService repositoryService;



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


}
