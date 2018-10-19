package com.hf.hadoop;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class WriteFile {
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();       
		FileSystem fs = FileSystem.get(URI.create("hdfs://hadoop1:9000"), conf);
		FSDataOutputStream out = fs.create(new Path("hdfs://hadoop1:9000/user/root/hfei"));
		out.write("this is a test".getBytes());
		out.hflush();
		FSDataInputStream in = fs.open(new Path("hdfs://hadoop1:9000/user/root/hfei"));
		IOUtils.copyBytes(in, System.out, 4096,true);
	}
}
