package cn.net.zhaozhiwen.file.server.utils;
 
import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
 
public class TestFastDfs {
     
    public static String conf_filename = "D:\\workspace\\testFastdfsUpload\\src\\fdfs_client.conf"; 
    //public String local_filename = "D:\\stsworkspace\\fastdfs-demo\\src\\main\\resources\\fdfs_client.conf";
     
    public static String local_filename = "D:\\downloads\\CmhnJFciJoSARFxvAACgAAC-Spw (1).tar.gz";
    
    public static String fileexits  = "group1/M00/00/00/CmhnJFcimziADvvHAACgAAC-Spw.tar.gz";
 
  public static void main(String[] args)  {
	  
	  if(args!=null ){
		  if(args.length==2){
			  conf_filename = args[0];
			  local_filename = args[1];
		  }else if(args.length==3){
			  conf_filename = args[0];
			  local_filename = args[1];
			  fileexits = args[2];
		  }
		  System.out.println("conf_filename = "+conf_filename);
		  System.out.println("local_filename = "+local_filename);
		  System.out.println("fileexits = "+fileexits);
	  }else{
		  System.out.println("not parameters");
	  }
	  testUpload();
	  System.out.println("-------------------------------");
	  testGetFileInfo();
	  System.out.println("-------------------------------");
	  testGetFileMate();
	  System.out.println("-------------------------------");
	  testDownload();
	  System.out.println("-------------------------------");
	  testDelete();
}
    public static  void testUpload() {
 
        try { 
            ClientGlobal.init(conf_filename);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
//          NameValuePair nvp = new NameValuePair("age", "18"); 
            NameValuePair nvp [] = new NameValuePair[]{ 
                    new NameValuePair("age", "18"), 
                    new NameValuePair("sex", "male") 
            }; 
            String fileIds[] = storageClient.upload_file(local_filename, "tar.gz", nvp);
             
            System.out.println(fileIds.length); 
            System.out.println("组名：" + fileIds[0]); 
            System.out.println("路径: " + fileIds[1]);
 
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (MyException e) { 
            e.printStackTrace(); 
        } 
    }
 
     
    public static void testDownload() {
        try {
 
            ClientGlobal.init(conf_filename);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
            byte[] b = storageClient.download_file("group1", fileexits); 
            System.out.println(b); 
            //IOUtils.write(b, new FileOutputStream("D:/"+UUID.randomUUID().toString()+".conf"));
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
     
     
    public static void testGetFileInfo(){ 
        try { 
            ClientGlobal.init(conf_filename);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
            FileInfo fi = storageClient.get_file_info("group1", fileexits); 
            System.out.println(fi.getSourceIpAddr()); 
            System.out.println(fi.getFileSize()); 
            System.out.println(fi.getCreateTimestamp()); 
            System.out.println(fi.getCrc32()); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
     
     
    public static void testGetFileMate(){ 
        try { 
            ClientGlobal.init(conf_filename);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, 
                    storageServer); 
            NameValuePair nvps [] = storageClient.get_metadata("group1", fileexits); 
            for(NameValuePair nvp : nvps){ 
                System.out.println(nvp.getName() + ":" + nvp.getValue()); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
     
     
    public static void testDelete(){ 
        try { 
            ClientGlobal.init(conf_filename);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, 
                    storageServer); 
            int i = storageClient.delete_file("group1", fileexits); 
            System.out.println( i==0 ? "删除成功" : "删除失败:"+i); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
}