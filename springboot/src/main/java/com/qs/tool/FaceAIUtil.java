package com.qs.tool;

import com.baidu.aip.face.AipFace;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaceAIUtil {

    private static Logger logger = Logger.getLogger(FaceAIUtil.class);

    //设置APPID/AK/SK
    public static final String APP_ID = "10445500";
    public static final String API_KEY = "XGZoVxzw0mrmaZC3zG2AGuQS";
    public static final String SECRET_KEY = "teqbGsmhH3Cir3RHkZmuGpGRGnShnVXX";
    public static final int CONNECT_TIME_OUT = 2000;
    public static final String AI_GROUP="springboot";

    /**
     * 人脸检测：检测照片中是否仅包含1个人脸
     * @param path
     * @return boolean true是 false否
     */
    public static boolean faceDetectByPath(String path){
        try{
            AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);
            HashMap<String,String> options = new HashMap<String, String>();
            options.put("max_face_num", "2");//设置最大检测人脸数为2个
            JSONObject res = client.detect(path, options);
            logger.info(res.toString());
            String num = res.get("result_num")+"";
            if("1".equals(num)){//检测到1个人脸时照片合格
                return true;
            }
        }catch (Exception e){
            logger.error("faceDetectByPath",e);
        }
        return false;
    }

    /**
     * 人脸检测：检测照片中是否仅包含1个人脸
     * @param base64
     * @return boolean true是 false否
     */
    public static boolean faceDetectByBase64(String base64){
        try{
            AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
            client.setConnectionTimeoutInMillis(CONNECT_TIME_OUT);
            HashMap<String,String> options = new HashMap<String, String>();
            options.put("max_face_num", "2");//设置最大检测人脸数为2个
            JSONObject res = client.detect(new BASE64Decoder().decodeBuffer(base64), options);
            logger.info(res.toString());
            String num = res.get("result_num")+"";
            if("1".equals(num)){//检测到1个人脸时照片合格
                return true;
            }
        }catch (Exception e){
            logger.error("faceDetectByBase64",e);
        }
        return false;
    }

    /**
     * 人脸检测,检测失败返回0
     * @param photoPath1
     * @param base64Str2
     * @return
     */
    public static String faceRecognizeComplex(String photoPath1,String base64Str2){
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            return faceRecognize(encoder.encodeBuffer(FileUtils.readFileToByteArray(new File(photoPath1))),base64Str2);
        } catch (IOException e) {
            logger.error("faceRecognizeComplex",e);
        }
        return "0";
    }

    /**
     * 人脸检测，检测失败返回0
     * @param base64Str1
     * @param base64Str2
     * @return
     */
    public static String faceRecognize(String base64Str1,String base64Str2) {
        try{
            AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
            client.setConnectionTimeoutInMillis(CONNECT_TIME_OUT);

            BASE64Decoder decoder = new BASE64Decoder();
            byte[][] arry =new byte[2][];
            arry[0]=decoder.decodeBuffer(base64Str1);
            arry[1]=decoder.decodeBuffer(base64Str2);
            JSONObject response = client.match(arry, new HashMap<String, String>());
            logger.info(response.toString());
            JSONObject scoreObject = (JSONObject) response.getJSONArray("result").get(0);
            return scoreObject.get("score").toString();
        }catch (Exception e){
            logger.error("faceRecognize",e);
        }
        return "0";
    }

    public static boolean addUser(String uid,String picPath) {
        try{
            AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
            client.setConnectionTimeoutInMillis(CONNECT_TIME_OUT);

            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("action_type", "replace");

            String userInfo = "add user"+uid;
            List<String> groupIds = new ArrayList();
            groupIds.add("rms");

            // 参数为本地图片路径
            JSONObject res = client.addUser(uid, userInfo, groupIds, picPath, options);
            logger.info(res.toString());

            if(res.toString().indexOf("error_code")<0){
                return true;
            }

        }catch (Exception e){
            logger.error("addUser",e);
        }
        return false;
    }

    public static boolean addUserBase64(String uid,String base64) {
        try{
            AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
            client.setConnectionTimeoutInMillis(CONNECT_TIME_OUT);

            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("action_type", "replace");

            String userInfo = "add user"+uid;
            List<String> groupIds = new ArrayList();
            groupIds.add("rms");

            // 参数为本地图片路径
            JSONObject res = client.addUser(uid, userInfo, groupIds, new BASE64Decoder().decodeBuffer(base64), options);
            logger.info(res.toString());

            if(res.toString().indexOf("error_code")<0){
                return true;
            }

        }catch (Exception e){
            logger.error("addUser",e);
        }
        return false;
    }

    public static String verifyUser(String uid,String picPath) {
        try{
            AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
            client.setConnectionTimeoutInMillis(CONNECT_TIME_OUT);

            // 传入可选参数调用接口
            HashMap<String, Object> options = new HashMap<String, Object>();

            List<String> groupIds = new ArrayList();
            groupIds.add(AI_GROUP);

            // 参数为本地图片路径
            JSONObject res = client.verifyUser(uid, groupIds, picPath, options);
            logger.info(res.toString());
            Object scoreObject = res.getJSONArray("result").get(0);

            return scoreObject.toString();
        }catch (Exception e){
            logger.error("verifyUser",e);
        }
        return "0";
    }

    public static String verifyUserBase64(String uid,String base64) {
        try{
            AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
            client.setConnectionTimeoutInMillis(CONNECT_TIME_OUT);

            // 传入可选参数调用接口
            HashMap<String, Object> options = new HashMap<String, Object>();

            List<String> groupIds = new ArrayList();
            groupIds.add(AI_GROUP);

            BASE64Decoder decoder = new BASE64Decoder();

            // 参数为本地图片路径
            JSONObject res = client.verifyUser(uid, groupIds, decoder.decodeBuffer(base64), options);
            logger.info(res.toString());
            Object scoreObject = res.getJSONArray("result").get(0);

            return scoreObject.toString();
        }catch (Exception e){
            logger.error("verifyUserBase64",e);
        }
        return "0";
    }

}
