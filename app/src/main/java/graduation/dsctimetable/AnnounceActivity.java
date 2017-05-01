//package graduation.dsctimetable;
//
//import android.app.ActionBar;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.net.Socket;
//
///**
// * Created by leejs on 2016-10-13.
// */
//
//public class AnnounceActivity extends AppCompatActivity {
//    private String html = "";
//    private Handler mHandler;
//
//    private Socket socket;
//    private String name;
//    private BufferedReader networkReader;
//    private BufferedWriter networkWriter;
//    private String ip = "222.107.244.34";
//    private int port = 4564;
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.announce_layout);
//        mHandler = new Handler();
//
//        try {
//            setSocket(ip, port);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//
//
//        final EditText in_text = (EditText) findViewById(R.id.inputanno);
//        Button send_button = (Button) findViewById(R.id.send_button);
//        final TextView out_text = (TextView) findViewById(R.id.output);
//
//        send_button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (!in_text.getText().toString().equals(null) || !in_text.getText().toString().equals("")) {
//                    PrintWriter out = new PrintWriter(networkWriter, true);
//                    String return_msg = in_text.getText().toString();
//                    out.println(return_msg);
//                }
//            }
//        });
//
////        Thread checkUpdate = new Thread() {
////            public void run() {
////                try {
////                    String line;
////                    Log.w("ChattingStart", "Start Thread");
////                    while(true) {
////                        Log.w("Chatting is running", "Chatting is running");
////                        line = networkReader.readLine();
////                        html = line;
////                        mHandler.post(showUpdate);
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////        };
////        checkUpdate.start();
////
////        Runnable showUpdate = new Runnable() {
////            public void run() {
////                Toast.makeText(AnnounceActivity.this, "Coming word :  "+html, Toast.LENGTH_SHORT).show();
////            }
////        };
//    }
//
//    private Thread checkUpdate = new Thread() {
//        public void run() {
//            try {
//                String line;
//                Log.w("ChattingStart", "Start Thread");
//                while (true) {
//                    Log.w("Chatting is running", "Chatting is running");
//                    line = networkReader.readLine();
//                    html = line;
//                    mHandler.post(showUpdate);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    private Runnable showUpdate = new Runnable() {
//        public void run() {
//            Toast.makeText(AnnounceActivity.this, "Coming word :  " + html, Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    public void setSocket(String ip, int port) throws IOException {
//        try {
//            socket = new Socket(ip, port);
//            networkWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        } catch (IOException e) {
//            System.out.println(e);
//            e.printStackTrace();
//        }
//    }
//}
//
////TODO OOOOOOOOOOOOOOOOOOOOOOOOOOOO
////public class AnnounceActivity extends AppCompatActivity {
////    private Socket socket;
////    BufferedReader socket_in;
////    PrintWriter socket_out;
////    String data;
//////    StringWriter stringWriter;
////
////    EditText myanno;
////    TextView output;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.announce_layout);
////
////        myanno = (EditText)findViewById(R.id.inputanno);
////        Button send_button = (Button)findViewById(R.id.send_button);
////        send_button.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String data = myanno.getText().toString();
//////                String data = new String("Gogo");
//////                stringWriter = new StringWriter();
//////                socket_out = new PrintWriter(stringWriter);
////                Log.w("NETWORK", " " + data);
////                if (!data.equals(null)) {
////                    socket_out.println(data);
////                }
////            }
////        });
////
////        output = (TextView)findViewById(R.id.output);
//////        LinearLayout linear = (LinearLayout)findViewById(R.id.linearanno);
//////        output = new TextView(AnnounceActivity.this);
//////
//////        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(1000, ActionBar.LayoutParams.WRAP_CONTENT);
//////        output.setLayoutParams(param);
//////        output.setTextSize(11);
//////        output.setPadding(10, 15, 10, 15);
//////        linear.addView(output);
////
////        Thread worker = new Thread() {
////            public void run() {
////                try {
////                    socket = new Socket("222.107.244.34", 4554);
////                    socket_out = new PrintWriter(socket.getOutputStream(), true);
////                    socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////                } catch(IOException e) {
////                    e.printStackTrace();
////                }
////
////                try {
////                    while(true) {
////                        data = socket_in.readLine();
////                        output.post(new Runnable() {
////                            public void run() {
////                                output.setText(data);
////                            }
////                        });
////                    }
////                } catch (Exception e) {
////                    //catch
////                }
////            }
////        };
////        worker.start();
////    }
////
////    @Override
////    protected void onStop() {
////        super.onStop();
////        try {
////            socket.close();
////        } catch(IOException e) {
////            e.printStackTrace();
////        }
////    }
////}
