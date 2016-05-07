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
 
public class FastDfsUtils {
     
    public static String conf_filename = "D:\\workspace\\testFastdfsUpload\\src\\fdfs_client.conf"; 
    //public String local_filename = "D:\\stsworkspace\\fastdfs-demo\\src\\main\\resources\\fdfs_client.conf";
     
    public static String local_filename = "D:\\downloads\\CmhnJFciJoSARFxvAACgAAC-Spw (1).tar.gz";
    
    public static String fileexits  = "group1/M00/00/00/CmhnJFcimziADvvHAACgAAC-Spw.tar.gz";
 
 
    
    public static  String[] upload(String cfile,byte[] bytes,String extName,NameValuePair[] nvp) {
    	 
        try { 
            ClientGlobal.init(cfile);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);  
            String fileIds[] = storageClient.upload_file(bytes, extName, nvp);
            //System.out.println(storageServer.getInetSocketAddress().getAddress());
            
            System.out.println(fileIds.length); 
            System.out.println("组名：" + fileIds[0]); 
            System.out.println("路径: " + fileIds[1]);
            return fileIds;
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (MyException e) { 
            e.printStackTrace(); 
        } 
        return null;
    }
    
    
    public static String[]   upload2(String cfile,String ufile,String extName,NameValuePair[] nvp) {
 
        try { 
            ClientGlobal.init(cfile);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
            
            String fileIds[] = storageClient.upload_file(ufile, extName, nvp);
            //System.out.println(storageServer.getInetSocketAddress().getAddress());
            
            System.out.println(fileIds.length); 
            System.out.println("组名：" + fileIds[0]); 
            System.out.println("路径: " + fileIds[1]);
            return fileIds;
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (MyException e) { 
            e.printStackTrace(); 
        } 
        return null;
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