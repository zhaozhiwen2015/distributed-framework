package cn.net.zhaozhiwen.file.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Configuration {

	@Value("${file.server.host}")
	private String fileServerHost;
	@Value("${tmp.convert.dir}")
	private String tmpConvertDir;

	public String getFileServerHost() {
		return fileServerHost;
	}

	public void setFileServerHost(String fileServerHost) {
		this.fileServerHost = fileServerHost;
	}

	public String getTmpConvertDir() {
		return tmpConvertDir;
	}

	public void setTmpConvertDir(String tmpConvertDir) {
		this.tmpConvertDir = tmpConvertDir;
	}
	
	
	
}
