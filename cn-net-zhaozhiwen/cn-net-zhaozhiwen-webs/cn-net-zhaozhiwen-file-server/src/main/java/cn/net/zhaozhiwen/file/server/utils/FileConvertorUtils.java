package cn.net.zhaozhiwen.file.server.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileConvertorUtils {
	public static String convert(final String prefixPath,final String fileName,final byte[] bytes ){
		 return writeFile(prefixPath, fileName,bytes );
	}
	/**
	 * 步骤
	 * 1.文件写入磁盘
	 * 2.调用ffmpeg转格式
	 * 3.返回转换后的磁盘路径
	 * @param prefixPath
	 * @param fileName
	 * @param bytes
	 * @return
	 */
	private static String writeFile(final String prefixPath,final String fileName,final byte[] bytes ){
		//1.文件写入磁盘
		System.out.println("prefixPath="+prefixPath);
	    File dir = new File(prefixPath);  
        if(!dir.exists()){//判断文件目录是否存在  
            dir.mkdirs();  
        }  
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        File file = null;
		try {
			file = new File(prefixPath,fileName); 
		    if(!file.exists()){
					file.createNewFile();
		    }
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);  
	        bos.write(bytes); 
	        bos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//2.调用ffmpeg转格式
		try {
			Runtime runtime = Runtime.getRuntime();
			String retPath = prefixPath.concat(File.separator).concat("A.mp3");
			String cmd = "ffmpeg -i ".concat(file.getPath()).concat(" ").concat(retPath);
			Process process = runtime.exec(cmd);
			int status = process.waitFor();
			System.out.println(cmd +" status " +status);
			//3.返回转换后的磁盘路径
			return retPath;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//3.返回转换后的磁盘路径
		return "";
	}
	
	public static boolean delDirectory(final String dirPath){
		return delDir(new File(dirPath));
	}
	private static boolean delDir(final File dir){
	    if (dir.isDirectory()) {
	    	String[] children = dir.list();
	            for (int i=0; i<children.length; i++) {
	                boolean success = delDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	     }
	     return dir.delete();
	}
	
}
