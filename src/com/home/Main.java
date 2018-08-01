package com.home;



import java.io.*;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws Exception {
        //writeTestData();
        //checkSampleNum();
//        checkSampleNum2();
        choiceFunc();
    }

    private static void choiceFunc() throws Exception
    {
        System.out.println("输入所需功能编号：");

        HashMap<String,String> funcMap = new HashMap<>();
        funcMap.put("0","-> 0：退出程序");
        funcMap.put("1","-> 1：对比两个样本，输出两个样本第一和第二列不同的数据");
        funcMap.put("2","-> 2：对比两个样本，输出第一个样本对于第二样本第一列的差集");

        HashMap<String,String> funcMap2 = new HashMap<>();
        funcMap2.put("1","checkSampleNum");
        funcMap2.put("2","checkSampleNum2");

        for(String key:funcMap.keySet())
        {
            System.out.println(funcMap.get(key));
        }

        Class<?> myClass = Class.forName("com.home.Main");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            String strNum="";

            while(!(strNum = reader.readLine()).equals("0"))
            {
                if(funcMap.containsKey(strNum))
                {
//                    Method method = Thread.currentThread().getStackTrace()[1].getClass().getMethod(funcMap2.get(strNum));
                    Method myMethod = myClass.getDeclaredMethod(funcMap2.get(strNum));
                    myMethod.invoke(null);
                    System.exit(0);
                }
                else if(strNum.equals("0"))
                {
                    System.exit(0);
                }
                else
                {
                    System.out.println("请重新输入功能编号");
                }
            }

        }
    }
    private static void writeTestData() throws IOException
    {

        try (FileWriter fw = new FileWriter("myFile02.txt", false);
             BufferedWriter out = new BufferedWriter(fw)) {
            System.out.println(new Date(System.currentTimeMillis())+":start...");
            for (int i = 250000; i < 270000; i++) {
                out.write(i+","+"test"+i);
                out.write("\r\n");
            }
            out.flush();
            out.close();
            fw.close();

            System.out.println(new Date(System.currentTimeMillis())+":end...");
        }
    }

    private static void checkSampleNum() throws IOException
    {
        String file1 = "";
        String file2 = "";

        System.out.println("");
        System.out.println("输入需要比对的两个样本数据：");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("输入样本数据1：");
            file1 = reader.readLine();

            System.out.println("输入样本数据2：");
            file2 = reader.readLine();

//            reader.close();
        }
        if(file1.equals("") || file2.equals("") || file1.equals(file2))
        {
            System.out.println("输入文件错误！");
            System.exit(0);
        }
        File file = new File(file1);
        if(!file.exists()) {
            System.out.println("样本1不存在");
            System.exit(0);
        }

        file = new File(file2);
        if(!file.exists()) {
            System.out.println("样本2不存在");
            System.exit(0);
        }

        System.out.println(new Date(System.currentTimeMillis())+": 比对开始...");

        String[] arr;
        String strData;
        HashMap<String,String> sampleMap1 = new HashMap<>();
        try(FileReader fileReader = new FileReader(file1);
            BufferedReader reader = new BufferedReader(fileReader))
        {
            int i=0;
            while((strData = reader.readLine())!=null)
            {
                i++;
                arr = strData.split(",");
                if(arr.length <1) continue;
                if(arr.length>1) {
                    sampleMap1.put(arr[0], arr[1]);
                }
                else
                {
                    sampleMap1.put(arr[0],"");
                }
            }
            System.out.println("样本1：读取"+i+"条");
            reader.close();
            fileReader.close();
        }

        long startTicks = System.currentTimeMillis();
        try (FileReader fileReader = new FileReader(file2);
             BufferedReader reader = new BufferedReader(fileReader)) {
            try (FileWriter fw = new FileWriter("result"+System.currentTimeMillis()+".txt", false);
                 BufferedWriter out = new BufferedWriter(fw)) {
                int i = 0;
                int j=0;
                String strValue="";
                while ((strData = reader.readLine()) != null) {

                    i++;
                    arr = strData.split(",");
                    if(arr.length <1) continue;
                    if (!sampleMap1.containsKey(arr[0])) {
                        j++;
                        out.write(strData);
                        out.write("\r\n");
                    }
                    else
                    {
                        if(arr.length>1)
                        {
                            strValue = sampleMap1.get(arr[0]);
                            if(!strValue.equals(arr[1]))
                            {
                                j++;
                                out.write(strData);
                                out.write(","+strValue);
                                out.write("\r\n");
                            }
                        }

                    }
                    System.out.println("对比样本2数据，第"+i+"条");
                }
                System.out.println("发现样本差异："+j+"条...");

                out.flush();
                out.close();
                fw.close();


            }


            reader.close();
            fileReader.close();
        }

        long endTicks = System.currentTimeMillis();
        long total = (endTicks-startTicks)/1000;
        System.out.println(new Date(System.currentTimeMillis()) + ": 比对结束...");
        System.out.println("耗时："+total+"秒");
    }



    private static void checkSampleNum2() throws IOException
    {
        String file1 = "";
        String file2 = "";

        System.out.println("");
        System.out.println("输入需要比对的两个样本数据：");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("输入样本数据1：");
            file1 = reader.readLine();

            System.out.println("输入样本数据2：");
            file2 = reader.readLine();

            reader.close();
        }
        if(file1.equals("") || file2.equals("") || file1.equals(file2))
        {
            System.out.println("输入文件错误！");
            System.exit(0);
        }
        File file = new File(file1);
        if(!file.exists()) {
            System.out.println("样本1不存在");
            System.exit(0);
        }

        file = new File(file2);
        if(!file.exists()) {
            System.out.println("样本2不存在");
            System.exit(0);
        }

        System.out.println(new Date(System.currentTimeMillis())+": 比对开始...");

        String[] arr;
        String strData;
        HashMap<String,String> sampleMap1 = new HashMap<>();
        try(FileReader fileReader = new FileReader(file1);
            BufferedReader reader = new BufferedReader(fileReader))
        {
            int i=0;
            while((strData = reader.readLine())!=null)
            {
                i++;
                arr = strData.split(",");
                if(arr.length <1) continue;
                if(arr.length>1) {
                    sampleMap1.put(arr[0], arr[1]);
                }
                else
                {
                    sampleMap1.put(arr[0],"");
                }
            }
            System.out.println("样本1：读取"+i+"条");
//            reader.close();
//            fileReader.close();
        }

        long startTicks = System.currentTimeMillis();
        try (FileReader fileReader = new FileReader(file2);
             BufferedReader reader = new BufferedReader(fileReader)) {

                int i = 0;
                int j = 0;
                while ((strData = reader.readLine()) != null) {

                    i++;
                    arr = strData.split(",");
                    if(arr.length <1) continue;
                    if (sampleMap1.containsKey(arr[0])) {
                        sampleMap1.remove(arr[0]);
                        j++;
                    }

                    System.out.println("对比样本2数据，第"+i+"条");
                }


                System.out.println("发现样本差异："+j+"条...");




//            reader.close();
//            fileReader.close();
        }


        try (FileWriter fw = new FileWriter("result"+System.currentTimeMillis()+".txt", false);
             BufferedWriter out = new BufferedWriter(fw)) {
            int i =0;
            for(String key:sampleMap1.keySet())
            {
                i++;
                System.out.println("输出样本数据：第"+i+"条");
                out.write(key);
                out.write(","+sampleMap1.get(key));
                out.write("\r\n");
            }
            out.flush();
        }

        long endTicks = System.currentTimeMillis();
        long total = (endTicks-startTicks)/1000;
        System.out.println(new Date(System.currentTimeMillis()) + ": 比对结束...");
        System.out.println("耗时："+total+"秒");
    }
}
