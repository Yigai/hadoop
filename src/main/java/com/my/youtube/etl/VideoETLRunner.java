package com.my.youtube.etl;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class VideoETLRunner implements Tool{

	private Configuration conf = null;
	public void setConf(Configuration conf) {
		this.conf = conf;
	}
	
	public Configuration getConf() {
		return this.conf;
	}

	public int run(String[] args) throws Exception {
		conf.set("inpath", args[0]);
		conf.set("outpath", args[1]);
		
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(VideoETLRunner.class);
		job.setMapperClass(VideoETLMapper.class);
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.setNumReduceTasks(0);
		
		this.initJobIntputPath(job);
		this.initJobOutputPath(job);
		
		return job.waitForCompletion(true) ? 1 : -1;
	}

	private void initJobOutputPath(Job job) throws IOException {
		Configuration conf = job.getConfiguration();
		String outPathString = conf.get("outpath");
		
		FileSystem fs =FileSystem.get(conf);
		Path outPath = new Path(outPathString);
		
		if(fs.exists(outPath)) {
			fs.delete(outPath, true);
		}
		
		FileOutputFormat.setOutputPath(job, outPath);
	}

	private void initJobIntputPath(Job job) throws IOException {
		Configuration conf = job.getConfiguration();
		String inPathString = conf.get("inpath");
		
		Path inPath = new Path(inPathString);
		FileSystem fs = FileSystem.get(conf);
		
		if(fs.exists(inPath)) {
			FileInputFormat.addInputPath(job, inPath);
		}else {
			throw new IOException("传输的输入路径不合法！");
		}
	}
	public static void main(String[] args) throws Exception {
		VideoETLRunner videoETLRunner =new VideoETLRunner();
		int resultCode = ToolRunner.run(videoETLRunner, args);
		System.exit(resultCode);
	}
}
