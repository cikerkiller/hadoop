package com.hf.hadoop;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

public class URLCat {
	static {
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}
	public static void main(String[] args) throws IOException {
		InputStream in =null;
		try {
			
			Configuration conf = new Configuration();
//			conf.set("fs.defaultFS", "hdfs://192.168.59.134");
//			FileSystem fs = FileSystem.get(new URI("hdfs://192.168.59.134:9000"), conf);
//			fs.copyFromLocalFile(new Path("D://OSExecute.java"), new Path("/user/root/huangfei/"));
			
			in = new URL("hdfs://localhost:9000/user/WIN10/hftest/OSExecute.java").openStream();
			IOUtils.copyBytes(in, System.out, 4096,false);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeStream(in);
		}
		
	}
}
