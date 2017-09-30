package com.xdandroid.sample;

import android.app.*;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.util.Log;
import android.view.*;

import com.xdandroid.hellodaemon.*;

import java.util.List;

public class MainActivity extends Activity {
    private IBookManagerInterface bookManagerInterface = null;
    private List<Book> books = null;
    private ServiceConnection aidlServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bookManagerInterface = IBookManagerInterface.Stub.asInterface(iBinder);
            if (bookManagerInterface == null)
                return;

            try {
                doAddbook("C", 48);
//                float price = 1.1f;
                double price = 1.1; //1.1f, 1.1
                doAddbook("Java", 1.1f);

                books = bookManagerInterface.getBooks();
                for (Book book: books) {
                    Log.e("Main", "book name " + book.getName() + ", price " + book.getPrice());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bookManagerInterface = null;
            books = null;
        }
    };

    private void doAddbook(String name, float price) throws RemoteException {
        if (bookManagerInterface == null)
            return;

        bookManagerInterface.addBook(new Book(name, price));
    }

    private void doBindService() {
        Intent intent = new Intent("xdandroid.aidl.test");
//        intent.setAction();
        intent.setPackage(getPackageName());

        bindService(intent, aidlServiceConnection, Context.BIND_AUTO_CREATE);
    }

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        doBindService();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                TraceServiceImpl.sShouldStopService = false;
                DaemonEnv.startServiceMayBind(TraceServiceImpl.class);
                break;
            case R.id.btn_white:
                IntentWrapper.whiteListMatters(this, "轨迹跟踪服务的持续运行");
                break;
            case R.id.btn_stop:
                TraceServiceImpl.stopService();
                break;
        }
    }

    //防止华为机型未加入白名单时按返回键回到桌面再锁屏后几秒钟进程被杀
    public void onBackPressed() {
        IntentWrapper.onBackPressed(this);
    }
}
