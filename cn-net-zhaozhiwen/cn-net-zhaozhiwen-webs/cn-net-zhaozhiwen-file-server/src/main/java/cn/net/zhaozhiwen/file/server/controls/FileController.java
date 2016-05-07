package cn.net.zhaozhiwen.file.server.controls;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.csource.common.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.net.zhaozhiwen.file.server.config.Configuration;
import cn.net.zhaozhiwen.file.server.utils.FastDfsUtils;
import cn.net.zhaozhiwen.file.server.utils.FileConvertorUtils;



@Controller
@RequestMapping("/file")
public class FileController {
	
	private final static String confFilePath = FileController.class.getClassLoader().getResource("fdfs_client.conf").getPath();
	private final static Logger LOGGER = Logger.getLogger(FileController.class);
	private static Random random = new Random();
	@Autowired
	Configuration config;
	/**
	 * 文件上传方法
	 * @param uploadFile
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam(value="uploadFile", required = false)MultipartFile uploadFile,HttpServletRequest req){     
		JSONObject json = new JSONObject();
		json.put("status", 0);
	     if (!uploadFile.isEmpty()) {
	    	 try {
	    		 String tmpPath = config.getTmpConvertDir()+random.nextInt(10000)+""+System.currentTimeMillis();
	    		 byte[] bytes = uploadFile.getBytes();
	    		 LOGGER.debug("是不是amr文件："+isAmrFile(bytes));
	    		 if(!isAmrFile(bytes)){
	    			 json.put("status", 0);
	    			 json.put("msg", "文件格式有问题，请上传amr文件");
	    			 return json.toJSONString();
	    		 }
	    		 
	    		 // if(LOGGER.isInfoEnabled()){
	    		 LOGGER.debug("文件信息：名称："+uploadFile.getName()+"、原名称:"+uploadFile.getOriginalFilename()+",大小："+bytes);	    			 
	    		// }
	 		     String retPath = FileConvertorUtils.convert(tmpPath, uploadFile.getOriginalFilename(), uploadFile.getBytes());
	 		     if(!StringUtils.isEmpty(retPath)){
	 		    	NameValuePair nvp [] = new NameValuePair[]{ 
		                     new NameValuePair("filesize", uploadFile.getSize()+""), 
		                     new NameValuePair("filename", uploadFile.getOriginalFilename()) 
		             };
		    		String[] fileId = FastDfsUtils.upload2(confFilePath, retPath,"mp3",nvp);	  
		    		if(!StringUtils.isEmpty(fileId)){
		    			//if(LOGGER.isInfoEnabled()){
		    			LOGGER.debug("组名：路径 ->" + fileId[0] +"--"+ fileId[1]);	    				
		    			//}
		    			json.put("status", 1);
		   	    	 	json.put("url", config.getFileServerHost()+"/"+fileId[0]+"/"+fileId[1]);
		   	    	    json.put("filesize", uploadFile.getSize());
		   	    	    json.put("filename", uploadFile.getOriginalFilename());
		    		}
	 		     }
	 		    FileConvertorUtils.delDirectory(tmpPath);
	    	 } catch (IOException e) {
				e.printStackTrace();
				json.put("status", 0);
		    	json.put("msg", "请上传文件出错了");
			}
	     }else{
	    	 json.put("status", 0);
	    	 json.put("msg", "请上传文件流");
	     }
		return json.toJSONString();
	}
	
	private static boolean isAmrFile(byte[]bytes){
		if(bytes.length<6){
			return Boolean.FALSE;
		 }
		if((((bytes[0]&0xff)^0x23)==0)
				&&(((bytes[1]&0xff)^0x21)==0)
				&&(((bytes[2]&0xff)^0x41)==0)
				&&(((bytes[3]&0xff)^0x4d)==0)
				&&(((bytes[4]&0xff)^0x52)==0)
				&&(((bytes[5]&0xff)^0x0a)==0)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@Deprecated
	@RequestMapping("/upload2")
	@ResponseBody
	public String upload2(Model model,String uploadFilePath){
	   
	     System.out.println("xxx");
		 String name= "";//userApiImpl.hello("zz");
	     System.out.println("xx=="+name);
		return "xx=="+name;
	}
	
	
	private String getExt(String fileName) {
        int lastIndexOf = fileName.lastIndexOf('.');
        String ext = fileName.lastIndexOf('.') < 0 ? fileName : fileName.substring(lastIndexOf + 1);
        return ext;
    }
	
	public static void main(String[] args) {
		
		String confFilePath = FileController.class.getClassLoader().getResource("fdfs_client.conf").getPath();
		File f = new File(confFilePath);
		if(f.exists()){
			
			System.out.println("file exist");
		}else{
			System.out.println("ssf");
		}
	     System.out.println(confFilePath);
	}
	
}