package com.digitalqd.production.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Python {
    @Value("${user.python.interpreter-path}")
    private String pythonInterpreterPath; // 解释器路径
    @Value("${user.python.file-path}")
    private String pyFilePath; // python文保存路径

    // private String env;
    // 以上代码测试用例:
    private String env = "dev";

    public String run(String pyFileName, String... inputs) {
        pythonInterpreterPath = "/opt/homebrew/Cellar/python@3.9/3.9.13_2/bin/python3.9";
        pyFilePath = "/Users/wizard/Documents/mycode/digitalqd/production/src/main/resources/static/python/";

        String result = null;

        List<String> arg = new ArrayList<>();
        if (env.equals("prod")) {
            arg.add("/bin/bash");
            arg.add("-c");
            String parm = "";
            for (var input : inputs) {
                parm += " '" + input + "'";
            }
            arg.add("python3 " + pyFilePath + pyFileName + parm);
        } else {
            arg.add(pythonInterpreterPath);
            arg.add(pyFilePath + pyFileName);
            for (var input : inputs) {
                arg.add(input);
            }
        }
        try {
            Process process = Runtime.getRuntime().exec(arg.toArray(new String[arg.size()]));
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 防止缓冲区满, 导致卡住
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    String line;
                    try {
                        BufferedReader stderr = new BufferedReader(
                                new InputStreamReader(process.getErrorStream(), "GBK"));
                        while ((line = stderr.readLine()) != null) {
                            System.out.println("stderr:" + line);
                        }
                    } catch (Exception e) {

                    }
                }
            }.start();

            String pyOut = null;
            // 读取输出数据
            while ((pyOut = in.readLine()) != null) {
                if (pyOut.startsWith("[") && pyOut.endsWith("]")) {
                    result = pyOut + "";
                }
            }
            in.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果python输出没有json对象，则返回null
        if (result == null) {
            return null;
        }
        // 否则返回map对象

        return result;
    }

}
