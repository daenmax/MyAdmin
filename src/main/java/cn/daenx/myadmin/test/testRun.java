package cn.daenx.myadmin.test;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.io.*;

public class testRun {
    public static void main(String[] args) {
        HttpResponse execute = HttpRequest.post("http://172.16.111.207:8082/second/open/download2").body("{}").execute();
        if (execute.getStatus() == 200) {
            //成功
            byte[] bytes = execute.bodyBytes();
            System.out.println("文件大小：" + bytes.length);
            boolean save = save2File("C:\\Users\\daen\\Desktop\\合同.pdf", bytes);
            System.out.println("文件保存：" + save);
        } else {
            //非200代表失败，此时json为失败的原因，json格式，例如：{"code":500,"success":false,"msg":"测试","timestamp":1685955232767}
            String json = execute.body();
            System.out.println("文件下载失败：" + json);
        }
    }


    public static boolean save2File(String fileName, byte[] msg) {
        OutputStream fos = null;
        try {
            File file = new File(fileName);
            File parent = file.getParentFile();
            boolean bool;
            if ((!parent.exists()) &&
                    (!parent.mkdirs())) {
                return false;
            }
            fos = new FileOutputStream(file);
            fos.write(msg);
            fos.flush();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            File parent;
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }


    /**
     * 读取txt文件
     *
     * @param filePath txt文件地址
     * @return
     */
    public static String readTxt(String filePath) {
        String content = "";
        try {
            File file = new File(filePath);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String lineTxt;
            while ((lineTxt = br.readLine()) != null) {
                content += lineTxt + "\n";
            }
            br.close();
        } catch (Exception e) {
            return null;
        }
        return content;
    }

}
