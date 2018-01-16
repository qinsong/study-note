package com.qs.controller;

import com.qs.entity.User;
import com.qs.service.IUserService;
import com.qs.tool.FaceAIUtil;
import com.qs.tool.LocalSoftInfoTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017/5/17.
 */
@Api(tags = "IndexController", description = "swagger test")
@RestController
public class IndexController {

    private static Logger loggger = Logger.getLogger(IndexController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private LocalSoftInfoTool localSoftInfoTool;

    @RequestMapping("/index")
    @ApiIgnore
    public ModelAndView index(Model modal) throws Exception {
        ModelAndView mav = new ModelAndView("index");
        /*User user = userService.queryUsers(new User());
        modal.addAttribute("user",user);*/

        InetAddress localHost = InetAddress.getLocalHost();
        modal.addAttribute("localHost",localHost);

        Map<String,String> envMap = System.getenv();
        modal.addAttribute("envMap",envMap);

        Properties sysProperty = System.getProperties();
        modal.addAttribute("sysProperty",sysProperty);

        modal.addAttribute("installInfo", localSoftInfoTool.getInstallSoftInfo());

        return mav;
    }

    @ApiOperation(value = "新增用户")
    @RequestMapping(value = "/addUser",method = {RequestMethod.GET})
    public String addUser(User user) {
        //user.setUsername("tx002");
        userService.addUser(user);
        return "success";
    }

    @RequestMapping("/recognize")
    public ModelAndView recognize(Model modal, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("recognize");
        String path = request.getContextPath();

         /*
         * /E:/gitworkspace/mrs/target/MRS/WEB-INF/classes/
         */
        String url = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        url = url.substring(0,url.indexOf("WEB-INF"));


        String base = "E:\\gitworkspace\\springbootweb\\src\\main\\webapp";
        String p1 = "/img/test/002-hg.jpg";
        String p2 = "/img/test/002-3f.jpg";
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("src1",path+p1);
        map1.put("src2",path+p2);
        map1.put("result", FaceAIUtil.faceRecognizeComplex(base+p1,base+p2));

        return mav;
    }
}
