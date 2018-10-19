package com.hf.hadoop;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.util.Tool;


public class WordCount2 implements Tool{
	public static class MyMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{
		private static final IntWritable one = new IntWritable(1);
		private Text text = new Text();
		@Override
		public void map(LongWritable key, Text value, OutputCollector<Text,IntWritable> output, Reporter reporter) throws IOException {
			StringTokenizer st = new StringTokenizer(value.toString());
			while (st.hasMoreTokens()) {
				text.set(st.nextToken());
				output.collect(text, one);
			}
			
		}

	}
	
	public static class MyReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output,
				Reporter reporter) throws IOException {
			int sum = 0;
			while(values.hasNext()) {
				int i = values.next().get();
				sum += i;
			}
			
			output.collect(key, new IntWritable(sum));
			
		}
		
	}
//	public static void main(String[] args) throws Exception {
//		int status = ToolRunner.run(new Configuration(), new WordCount2(), args);
//		System.exit(status);
//	}
	private Configuration conf;
	@Override
	public Configuration getConf() {
		return conf;
	}
	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
		
	}
	@Override
	public int run(String[] args) throws Exception {
		JobConf  job = new JobConf();
		job.setJarByClass(WordCount2.class);
		job.setJobName("wordcount");
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		return 0;
	}
}
