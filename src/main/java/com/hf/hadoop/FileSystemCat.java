package com.hf.hadoop;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileSystemCat {
	// 当不能使用urlstreamhandlerfactory
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		InputStream in = null;
		FSDataOutputStream out = null;
		try {
//			in = new BufferedInputStream(new FileInputStream("D://OSExecute.java"));
			FileSystem  fs = FileSystem.get(URI.create("hdfs://hadoop1:9000/user/root/"), conf);
			FileStatus[] listStatus = fs.listStatus(new Path("hdfs://hadoop1:9000/user/root/"));
			for(FileStatus fst : listStatus) {
				String owner = fst.getOwner();
				System.out.println(owner);
				long len = fst.getLen();
				System.out.println(len);
			}
//			boolean status = fs.deleteOnExit(new Path("hdfs://hadoop1:9000/user/root/OSExecute1"));
//			System.exit(status ? 0 : 1);
//			out = fs.create(new Path("hdfs://hadoop1:9000/user/root/OSExecute1"),new Progressable() {
//				
//				@Override
//				public void progress() {
//					System.out.println("复制");
//				}
//			});
//			IOUtils.copyBytes(in, out, 4096,true);
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
		}
	}
}
