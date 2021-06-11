package cn.ulyer.activiti.api;

import cn.hutool.core.io.IoUtil;
import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class StencilseRestResource {


    private static String jsonStr;

    static{
        try {
            InputStream stream = new ClassPathResource("stencilset.json").getInputStream();
            jsonStr = IoUtil.read(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(
            value = {"/editor/stencilset"},
            method = {RequestMethod.GET},
            produces = {"application/json;charset=utf-8"}
    )
    @ResponseBody
    public String getStencilset() {
        return jsonStr;
    }

}
