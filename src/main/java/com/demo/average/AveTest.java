package com.demo.average;
/*
求平均值
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class AveTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "average");

        job.setJarByClass(AveTest.class);
        job.setMapperClass(AveMap.class);
        job.setReducerClass(AveReduce.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setNumReduceTasks(1);

        FileInputFormat.addInputPath(job, new Path("input/goods_click"));
        Path path = new Path("output/com/average");
        FileOutputFormat.setOutputPath(job, path);
        path.getFileSystem(conf).delete(path, true);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
