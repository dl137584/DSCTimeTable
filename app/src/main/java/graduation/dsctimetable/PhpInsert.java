//package graduation.dsctimetable;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import graduation.dsctimetable.list.AnnoListItem;
//
///**
// * Created by leejs on 2016-10-22.
// */
//public class PhpInsert extends AsyncTask<String, Integer, String> {
//
////    ProgressDialog loading;
////    private static final String REGISTER_URL = "http://172.30.1.26:80/dscapp/insertappdb.php";
////    private static final String REGISTER_URL = "http://192.168.1.20:80/dscapp/insertappdb.php";
//    private static final String REGISTER_URL = "http://172.30.62.146:80/dscapp/insertappdb.php";
//
//    String dept_name;
//    String year_no;
//    String g_name;
//    String stu_no;
//    String anno_info;
//    String anno_date;
//
//    public PhpInsert(String dept_name, String year_no, String g_name, String stu_no, String anno_info, String anno_date) {
//        this.dept_name = dept_name;
//        this.year_no = year_no;
//        this.g_name = g_name;
//        this.anno_info = anno_info;
//        this.anno_date = anno_date;
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
////        try {
////            String dept_name = (String)params[0];
////            String year_no = (String)params[1];
////            String g_name = (String)params[2];
////            String stu_no = (String)params[3];
////            String anno_info = (String)params[4];
////            String anno_date = (String)params[5];
////
////            //TODO Wifi checking
////            String link = "http://172.30.1.26:80/dscapp/insertappdb.php";
////            String data = URLEncoder.encode("dept_name", "euc-kr") + "-" + URLEncoder.encode(dept_name, "euc-kr");
////            data += "&" + URLEncoder.encode("year_no", "euc-kr") + "-" + URLEncoder.encode(year_no, "euc-kr");
////            data += "&" + URLEncoder.encode("g_name", "euc-kr") + "-" + URLEncoder.encode(g_name, "euc-kr");
////            data += "&" + URLEncoder.encode("stu_no", "euc-kr") + "-" + URLEncoder.encode(stu_no, "euc-kr");
////            data += "&" + URLEncoder.encode("anno_info", "euc-kr") + "-" + URLEncoder.encode(anno_info, "euc-kr");
////            data += "&" + URLEncoder.encode("anno_date", "euc-kr") + "-" + URLEncoder.encode(anno_date, "euc-kr");
////
////            URL url = new URL(link);
////            URLConnection conn = url.openConnection();
////
////            conn.setDoOutput(true);
////            OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream());
////
////            w.write(data);
//////            w.flush();
////
////            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
////
////            StringBuilder sbuilder = new StringBuilder();
////            String line = null;
////
////            //Read Server Response
////            while((line = reader.readLine()) != null) {
////                sbuilder.append(line);
////                break;
////            }
////            return sbuilder.toString();
////        } catch(Exception e) {
////            return new String("Exception : " + e.getMessage());
////        }
//
//        PhpInsert pi = new PhpInsert(dept_name, year_no, g_name, stu_no, anno_info, anno_date);
//
//        HashMap<String, String> data = new HashMap<String, String>();
//        data.put("dept_name", params[0]);
//        data.put("year_no", params[1]);
//        data.put("g_name", params[2]);
//        data.put("stu_no", params[3]);
//        data.put("anno_info", params[4]);
//        data.put("anno_date", params[5]);
//
//        String result = pi.sendPostRequest(REGISTER_URL, data);
//        return result;
//    }
//
//    @Override
//    protected void onPostExecute(String str) {
//        Log.d("WebDB", str);
//        super.onPostExecute(str);
////        loading.dismiss();
//    }
//
////    @Override
////    protected void onPreExecute() {
////        super.onPreExecute();
////        loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
////    }
//
//    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
//        Log.e("phpinsert", "send Post request");
//        URL url;
//        String response = "";
//
//        try {
//            url = new URL(requestURL);
//
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setReadTimeout(10000);
//            conn.setConnectTimeout(10000);
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "euc-kr"));
//            writer.write(getPostDataString(postDataParams));
//            writer.flush();
//            writer.close();
//            os.close();
//
//            int responseCode = conn.getResponseCode();
//
//            if(responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                response = br.readLine();
//            } else {
//                response = "Error Registering";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//        for(Map.Entry<String, String> entry : params.entrySet()){
//            Log.e("phpinsert", "map entry");
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            result.append(URLEncoder.encode(entry.getKey(), "euc-kr"));
//            result.append("=");
//            result.append(URLEncoder.encode(entry.getValue(), "euc-kr"));
//        }
//
//        return result.toString();
//    }
////    PhpInsert p = new PhpInsert(dept_name, year_no, g_name, stu_no, anno_info, anno_date);
////    p.execute(dept_name, year_no, g_name, stu_no, anno_info, anno_date)
//}
