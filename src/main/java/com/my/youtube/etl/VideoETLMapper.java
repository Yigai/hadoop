package com.my.youtube.etl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.my.youtube.utils.ETLUtil;

public class VideoETLMapper extends Mapper<Object, Text, NullWritable, Text> {

	private Text result = new Text();
	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, NullWritable, Text>.Context context)
			throws IOException, InterruptedException {
		String etlString = ETLUtil.getETLString(value.toString());
		if(etlString != null) {
		result.set(etlString);
		context.write(NullWritable.get(),result);
		}
	}	
}
