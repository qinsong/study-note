package com.qs.tool;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/19.
 */
@Component
public class LocalSoftInfoTool {
    private static String SOFT_REG_URL = "cmd /c reg query HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\";
    private static String DISPLAY_NAME = "DisplayName";
    private static String DISPLAY_VERSION = "DisplayVersion";
    private static String INSTALL_LOCATION = "InstallLocation";
    private static String PUBLISHER = "Publisher";
    private static String INSTALL_DATE = "InstallDate";

    private static String REG_SZ = "REG_SZ";

    public List getInstallSoftInfo() throws Exception {
        List<Map<String,String>> list = new ArrayList<>();

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(SOFT_REG_URL);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String regDir ;
        C:while ((regDir = in.readLine()) != null) {
            process = runtime.exec("cmd /c reg query " + regDir  + " /s");//查询所有项
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Map<String,String> map = new HashMap<>();
            String item ;
            String name = null;
            String version ;
            String installLocation ;
            String publisher ;
            String installDate ;
            while ((item=br.readLine())!=null){
                if(item.indexOf(DISPLAY_NAME)>0){
                    name = item.replaceAll(" ","").replace(DISPLAY_NAME+REG_SZ,"");
                    map.put(DISPLAY_NAME,name);
                }
                if(item.indexOf(DISPLAY_VERSION)>0){
                    version = item.replaceAll(" ","").replace(DISPLAY_VERSION+REG_SZ,"");
                    map.put(DISPLAY_VERSION,version);
                }
                if(item.indexOf(INSTALL_LOCATION)>0){
                    installLocation = item.replaceAll(" ","").replace(INSTALL_LOCATION+REG_SZ,"");
                    map.put(INSTALL_LOCATION,installLocation);
                }
                if(item.indexOf(PUBLISHER)>0){
                    publisher = item.replaceAll(" ","").replace(PUBLISHER+REG_SZ,"");
                    map.put(PUBLISHER,publisher);
                }
                if(item.indexOf(INSTALL_DATE)>0){
                    installDate = item.replaceAll(" ","").replace(INSTALL_DATE+REG_SZ,"");
                    map.put(INSTALL_DATE,installDate);
                }

            }
            if(StringUtils.isEmpty(name)){
                continue C;
            }
            list.add(map);
        }
        in.close();
        process.destroy();

        return list;
    }

}
