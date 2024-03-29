package com.yyz.car;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class CarTest {
    public static void main(String[] args) throws IOException, ClassCastException, InternalError, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        //创建一个job任务对象
        Job job = Job.getInstance(conf, "linecount");
        //打包出错，加此配置

        job.setJarByClass(CarTest.class);
        //指定Map阶段的处理方式和数据类型
        job.setMapperClass(CarMapper.class);
        //      指定K1的类型
        job.setMapOutputKeyClass(Text.class);
        //      指定v1的类型
        job.setMapOutputValueClass(IntWritable.class);

        //指定Reduce阶段的处理方式和数据类型
        job.setReducerClass(CarReduce.class);
        //      指定K2的类型
        job.setOutputKeyClass(Text.class);
        //      指定v2的类型
        job.setOutputValueClass(NullWritable.class);
//        job.setNumReduceTasks(1);

        FileInputFormat.addInputPath(job, new Path("demo-car/input/car.csv"));
        Path path = new Path("demo-car/output");
        FileOutputFormat.setOutputPath(job, path);

        path.getFileSystem(conf).delete(path, true);
        //启动job
        System.exit(job.waitForCompletion(true) ? 0 : 1);

//        path.getFileSystem(conf).delete(path, true);
//        if (job.waitForCompletion(true) ? true : false) {
//            System.out.println("num:" + job.getCounters().findCounter(CountEnum.TotalRecorder).getValue());
    }
}

