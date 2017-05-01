package graduation.dsctimetable;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by leejs on 2016-10-24.
 */
public class PhpRequest {
    URL url;

    public PhpRequest(URL url) {
        this.url = url;
    }

    public String Phptest(final String dept_name, final String year_no, final String g_name, final String stu_no
            , final String anno_info, final String anno_date) {
        try {
            Log.i("PHPRequest", "request start");
            String postData = "dept_name=" + dept_name + "&" + "year_no=" + year_no + "&" + "g_name=" + g_name
                    + "&" + "stu_no=" + stu_no + "&" + "anno_info=" + anno_info + "&" + "anno_date=" + anno_date;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            Log.i("PHPRequest", "connection");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(1000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            Log.i("PHPRequest", "request setting");
            OutputStream outputStream = conn.getOutputStream();
            Log.i("PHPRequest", "setting end");
            outputStream.write(postData.getBytes("EUC-KR"));
            outputStream.flush();
            Log.i("PHPRequest", "flushed");
            outputStream.close();

//            String result = readStream(conn.getInputStream());
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
//            System.out.println(result.toString());

            conn.disconnect();
            return result.toString();
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }
}
