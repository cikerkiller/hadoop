package com.hf.hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class WordCount {
	public static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		private static final IntWritable one = new IntWritable(1);
		private Text text = new Text();

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			StringTokenizer st = new StringTokenizer(value.toString());
			while (st.hasMoreTokens()) {
				text.set(st.nextToken());
				context.write(text, one);
			}
		}
	}

	public static class MyReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable it : values) {
				sum += it.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception {
		args = new String[] {"/user/WIN10/hftest/OSExecute.java","/user/WIN10/huangfei4"};
		Job job = new Job();
		job.setJarByClass(WordCount.class);
		job.setJobName("wordcount");
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReduce.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		SequenceFileInputFormat.addInputPath(job, new Path(args[0]));
		SequenceFileOutputFormat.setOutputPath(job, new Path(args[1]));
//		FileInputFormat.addInputPath(job, new Path(args[0]));
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
