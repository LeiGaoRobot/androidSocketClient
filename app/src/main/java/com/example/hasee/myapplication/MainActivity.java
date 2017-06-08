package com.example.hasee.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hasee.util.Const;
import com.example.hasee.util.FileUtil;
import com.example.hasee.util.TCPClient;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btSend;
    private EditText etPort_1, etUrl_1, etPort_2, etUrl_2, etPort_3, etUrl_3, etPort_4, etUrl_4, etPort_5, etUrl_5;
    private TextView tvResult;

    private boolean threadState = false;



    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String msgStr1 = (String) msg.obj;
                    Toast.makeText(MainActivity.this, msgStr1, Toast.LENGTH_LONG).show();
                    tvResult.setText("接收出现错误，终止接收！");
                    break;
                case 2:
                    String msgStr2 = (String) msg.obj;
                    //Toast.makeText(MainActivity.this,msgStr2,Toast.LENGTH_LONG).show();
                    tvResult.setText(msgStr2);
                    break;

            }
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermissions();
        etPort_1 = (EditText) findViewById(R.id.etPort_1);
        etUrl_1 = (EditText) findViewById(R.id.etUrl_1);
        etPort_2 = (EditText) findViewById(R.id.etPort_2);
        etUrl_2 = (EditText) findViewById(R.id.etUrl_2);
        etPort_3 = (EditText) findViewById(R.id.etPort_3);
        etUrl_3 = (EditText) findViewById(R.id.etUrl_3);
        etPort_4 = (EditText) findViewById(R.id.etPort_4);
        etUrl_4 = (EditText) findViewById(R.id.etUrl_4);
        etPort_5 = (EditText) findViewById(R.id.etPort_5);
        etUrl_5 = (EditText) findViewById(R.id.etUrl_5);
        btSend = (Button) findViewById(R.id.btSend);
        tvResult = (TextView) findViewById(R.id.tvResult);

        btSend.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileUtil.makeRootDirectory(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmpFiles/log");
                    if (etUrl_1.getText() != null && !"".equals(etUrl_1.getText().toString()) && etPort_1.getText() != null && !"".equals(etPort_1.getText().toString())) {
                         //TCPClient tcpClient = TCPClient.instance();
                         //tcpClient.connect("192.168.1.10",6340);
                         new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                   // socketConnect(etUrl_1.getText().toString(), Integer.parseInt(etPort_1.getText().toString()));
                                    Message msg = new Message();
                                    msg.what = 2;
                                    msg.obj = "线程一接收成功！";
                                    handler.sendMessage(msg);
                                } catch (Exception e) {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = "线程一出现错误：" + e.getMessage();
                                    handler.sendMessage(msg);
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                    if (etUrl_2.getText() != null && !"".equals(etUrl_2.getText().toString()) && etPort_2.getText() != null && !"".equals(etPort_2.getText().toString())) {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = "线程二开始接收！";
                        handler.sendMessage(msg);
                        Const.UTL = etUrl_2.getText().toString();
                        Const.PORT = Integer.parseInt(etPort_2.getText().toString());
                        connect(Const.UTL,Const.PORT);
                        revMsg();
                    }
                    if (etUrl_3.getText() != null && !"".equals(etUrl_3.getText().toString()) && etPort_3.getText() != null && !"".equals(etPort_3.getText().toString())) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //socketConnect(etUrl_3.getText().toString(), Integer.parseInt(etPort_3.getText().toString()));
                                    Message msg = new Message();
                                    msg.what = 2;
                                    msg.obj = "线程三接收成功！";
                                    handler.sendMessage(msg);
                                } catch (Exception e2) {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = "线程三出现错误：" + e2.getMessage();
                                    handler.sendMessage(msg);
                                }
                            }
                        }).start();
                    }
                    if (etUrl_4.getText() != null && !"".equals(etUrl_4.getText().toString()) && etPort_4.getText() != null && !"".equals(etPort_4.getText().toString())) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //socketConnect(etUrl_4.getText().toString(), Integer.parseInt(etPort_4.getText().toString()));
                                    Message msg = new Message();
                                    msg.what = 2;
                                    msg.obj = "线程四接收成功！";
                                    handler.sendMessage(msg);
                                } catch (Exception e) {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = "线程四出现错误：" + e.getMessage();
                                    handler.sendMessage(msg);
                                }
                            }
                        }).start();
                    }
                    if (etUrl_5.getText() != null && !"".equals(etUrl_5.getText().toString()) && etPort_5.getText() != null && !"".equals(etPort_5.getText().toString())) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                   // socketConnect(etUrl_2.getText().toString(), Integer.parseInt(etPort_2.getText().toString()));
                                    Message msg = new Message();
                                    msg.what = 2;
                                    msg.obj = "线程五接收成功！";
                                    handler.sendMessage(msg);
                                } catch (Exception e) {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = "线程五出现错误：" + e.getMessage();
                                    handler.sendMessage(msg);
                                }
                            }
                        }).start();
                    }
                } catch (Exception e) {

                }

            }


        });


    }

    static final String[] PERMISSION = new String[]{
            Manifest.permission.READ_CONTACTS,// 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,  //读取权限
            Manifest.permission.WRITE_CALL_LOG,        //读取设备信息
    };

    /**
     * 设置Android6.0的权限申请
     */
    private void setPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //Android 6.0申请权限
            ActivityCompat.requestPermissions(this, PERMISSION, 1);
        } else {
            Log.i("TAG", "权限申请ok");
        }
    }



    /** 信道选择器 */
    private Selector mSelector;

    /** 服务器通信的信道 */
    private SocketChannel mChannel;

    /** 远端服务器ip地址 */
    private String mRemoteIp;

    /** 远端服务器端口 */
    private int mPort;

    /** 是否加载过的标识 */
    private boolean mIsInit = false;

    /** 单键实例 */
    private static TCPClient gTcp;

    private TCPClient.TCPClientEventListener mEventListener;

    /** 默认链接超时时间 */
    public static final int TIME_OUT = 10000;

    /** 读取buff的大小 */
    public static final int READ_BUFF_SIZE = 1024;

    /** 消息流的格式 */
    public static final String BUFF_FORMAT = "utf-8";


    /**
     * 链接远端地址
     * @param remoteIp
     * @param port
     * @param tcel
     * @return
     */
    public void connect( String remoteIp, int port, TCPClient.TCPClientEventListener tcel ) {
        mRemoteIp = remoteIp;
        mPort = port;
        mEventListener = tcel;
        connect();
    }

    /**
     * 链接远端地址
     * @param remoteIp
     * @param port
     * @return
     */
    public void connect( String remoteIp, int port ) {
        connect(remoteIp,port,null);
    }

    private void connect() {
        //需要在子线程下进行链接
        MainActivity.MyConnectRunnable connect = new MainActivity.MyConnectRunnable();
        new Thread(connect).start();
    }

    /**
     * 发送字符
     * @param msg
     * @return
     */
    public boolean sendMsg(String msg) {
        boolean bRes = false;
        try {
            bRes = sendMsg(msg.getBytes(BUFF_FORMAT));
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return bRes;
    }

    /**
     * 发送数据,此函数需要在独立的子线程中完成,可以考虑做一个发送队列
     * 自己开一个子线程对该队列进行处理,就好像connect一样
     * @param bt
     * @return
     */
    public boolean sendMsg( byte[] bt ) {
        boolean bRes = false;
        if ( !mIsInit ) {

            return bRes;
        }
        try {
            ByteBuffer buf = ByteBuffer.wrap(bt);
            int nCount = mChannel.write(buf);
            if ( nCount > 0 ) {
                bRes = true;
            }
        } catch ( Exception e ) {
            Message msg = new Message();
            msg.what = 2;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
        }

        return bRes;
    }

    public Selector getSelector() {
        return mSelector;
    }

    /**
     * 是否链接着
     * @return
     */
    public boolean isConnect() {
        if ( !mIsInit ) {
            return false;
        }
        return mChannel.isConnected();
    }

    /**
     * 关闭链接
     */
    public void close() {
        mIsInit = false;
        mRemoteIp = null;
        mPort = 0;
        try {
            if ( mSelector != null ) {
                mSelector.close();
            }
            if ( mChannel != null ) {
                mChannel.close();
            }
        } catch ( Exception e ) {
            Message msg = new Message();
            msg.what = 2;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
            return;
        }
    }

    /**
     * 重连
     * @return
     */
    public void reConnect() {
        close();
        connect();
    }

    /**
     * 发送一个测试数据到服务器,检测服务器是否关闭
     * @return
     */
    public boolean canConnectServer() {
        boolean bRes = false;
        if ( !isConnect() ) {
            return bRes;
        }
        try {
            mChannel.socket().sendUrgentData(0xff);
        } catch ( Exception e ) {
            Message msg = new Message();
            msg.what = 2;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
            return false;
        }
        return bRes;
    }

    /**
     * 每次读完数据后,需要重新注册selector读取数据
     * @return
     */
    private synchronized boolean repareRead() {
        boolean bRes = false;
        try {
            //打开并注册选择器到信道
            mSelector = Selector.open();
            if ( mSelector != null ) {
                mChannel.register(mSelector, SelectionKey.OP_READ);
                bRes = true;
            }
        } catch ( Exception e ) {
            Message msg = new Message();
            msg.what = 2;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
            e.printStackTrace();
            return false;
        }
        return bRes;
    }

    public void revMsg() {
        if ( mSelector == null ) {
            return;
        }
        boolean bres = true;
        Long total_len = 1L;
        int rev_total = 0;
        int count = 0;
        ByteBuffer readBuffer_len = ByteBuffer.allocate(4);
        //读取缓存
        ByteBuffer readBuffer = ByteBuffer.allocate(READ_BUFF_SIZE);
        String dataFileName = "rev_"+Const.UTL+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+".dat";
        FileUtil fileUtil = new FileUtil();
        while ( mIsInit ) {
            if ( !isConnect() ) {
                bres = false;
            }
            if ( !bres ) {
                try {
                    Thread.sleep(100);
                } catch ( Exception e ) {
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = e.getMessage();
                    handler.sendMessage(msg);
                    e.printStackTrace();
                    return;
                }

                continue;
            }
            try {
                //有数据就一直接收
                while (mIsInit && mSelector.select() > 0) {
                    for ( SelectionKey sk : mSelector.selectedKeys() ) {
                        //如果理论接收长度与实际接收长度相等，则结束
                        if(total_len == rev_total){
                            fileUtil.writeTxtToFile("Save File : "+Environment.getExternalStorageDirectory().getAbsolutePath()+"/tmpFiles"+dataFileName,Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmpFiles/log","/rev_"+Const.UTL+".log");
                            Message msg = new Message();
                            msg.what =2;
                            msg.obj = "接收数据完毕，共接收"+rev_total+"字节";
                            handler.sendMessage(msg);
                            close();
                            return;
                        }
                        //如果有可读数据
                        if ( sk.isReadable() ) {
                            //使用NIO读取channel中的数据
                            SocketChannel sc = (SocketChannel)sk.channel();
                            //实际的读取流
                            ByteArrayOutputStream read = new ByteArrayOutputStream();
                            int nRead = 0;
                            int nLen = 0;
                            //单个读取流
                            byte[] bytes;
                            if(count == 0){
                                sc.read(readBuffer_len);
                                readBuffer_len.flip();
                                bytes = new byte[4];
                                readBuffer_len.get(bytes);
                                readBuffer_len.clear();
                                total_len = FileUtil.BytesToLong(bytes);
                                fileUtil.writeTxtToFile("Recved 4Bytes head Recving Data ["+total_len+"]Bytes：\n",Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmpFiles/log","/rev_"+Const.UTL+".log");
                            }
                            //读完为止
                            while ( (nRead = sc.read(readBuffer) ) > 0 ) {
                                //整理
                                readBuffer.flip();
                                bytes = new byte[nRead];
                                nLen += nRead;
                                //将读取的数据拷贝到字节流中
                                readBuffer.get(bytes);
                                //将字节流添加到实际读取流中
                                read.write(bytes);
                                /////////////////////////////////////
                                //@ 需要增加一个解析器,对数据流进行解析

                                /////////////////////////////////////
                                readBuffer.clear();
                            }
                            if ( nLen > 0 ) {
                                if ( mEventListener != null ) {
                                    mEventListener.recvMsg(read);
                                } else {
                                    String info = new String(read.toString(BUFF_FORMAT));
                                    Message msg = new Message();
                                    msg.what = 2;
                                    msg.obj = "本轮接收"+info.length()+"，共"+count+"轮";
                                    handler.sendMessage(msg);
                                    fileUtil.writeTxtToFile(new SimpleDateFormat("yyyy-MM-ddhh:mm:ssSSS").format(new Date())+"\t"+info.length()+"\n",Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmpFiles/log","/rev_"+Const.UTL+".log");
                                    rev_total += info.length();
                                    fileUtil.writeTxtToFile(info,Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmpFiles/",dataFileName);
                                    Log.i("TCPClient",info.length()+"----------"+Const.UTL);
                                    Log.i("TCPClient：",info);
                                    Log.i("接收数据总长度（实际）：",rev_total+"");
                                    Log.i("接收数据总长度(理论)：",total_len+"");
                                    count++;
                                    read.close();
                                    sendMsg("Q");
                                }
                            }

                            //为下一次读取做准备
                            sk.interestOps(SelectionKey.OP_READ);
                        }

                        //删除此SelectionKey
                        mSelector.selectedKeys().remove(sk);
                        count++;
                    }
                }
            } catch ( Exception e ) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = e.getMessage();
                handler.sendMessage(msg);
                e.printStackTrace();
                return;
            }
        }
    }

    public interface TCPClientEventListener {
        /**
         * 多线程下接收到数据
         * @param read
         * @return
         */
        void recvMsg(ByteArrayOutputStream read);
    }

    /**
     * 链接线程
     * @author HeZhongqiu
     *
     */
    private class MyConnectRunnable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                //打开监听信道,并设置为非阻塞模式
                SocketAddress ad = new InetSocketAddress(mRemoteIp, mPort);
                mChannel = SocketChannel.open( ad );
                if ( mChannel != null ) {
                    mChannel.socket().setTcpNoDelay(false);
                    mChannel.socket().setKeepAlive(true);

                    //设置超时时间
                    mChannel.socket().setSoTimeout(TIME_OUT);
                    mChannel.configureBlocking(false);

                    mIsInit = repareRead();

                    //创建读线程
                    MainActivity.RevMsgRunnable rev = new MainActivity.RevMsgRunnable();
                    new Thread(rev).start();
                }
            } catch ( Exception e ) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = e.getMessage();
                handler.sendMessage(msg);
                e.printStackTrace();
                return;
            } finally {
                if ( !mIsInit ) {
                    close();
                }
            }
        }
    }

    private class RevMsgRunnable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            revMsg();
        }

    }


}

