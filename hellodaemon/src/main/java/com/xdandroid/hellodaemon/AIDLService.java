package com.xdandroid.hellodaemon;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
//import com.xdandroid.hellodaemon.IBookManagerInterface;

/**
 * Created by win7 on 2017/9/29.
 */

public final class AIDLService extends Service {
    private final static String TAG = "AIDLService";
//    private final String TAG = this.getClass().getSimpleName();
    private List<Book> mBooks = new ArrayList<>();

    private final IBookManagerInterface.Stub  bookManagerInterface = new IBookManagerInterface.Stub() {

        @Override
        public List<com.xdandroid.hellodaemon.Book> getBooks() throws RemoteException {
            return mBooks;
        }

        @Override
        public void addBook(com.xdandroid.hellodaemon.Book book) throws RemoteException {
            if (book == null)
                return;
            if (!mBooks.contains(book))
                mBooks.add(book);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return bookManagerInterface;
    }
}
