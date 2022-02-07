package com.ajal.arsocialmessaging.util.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ajal.arsocialmessaging.util.ConnectivityHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBResults {
    private static final String TAG = "SkyWrite";
    private List<Message> messages;
    private List<Banner> banners;
    private List<DBObserver> observers = new ArrayList<>();
    private static DBResults instance = new DBResults();

    public static DBResults getInstance() {
        return instance;
    }

    // Private so it cannot be instantiated outside of getInstance
    private DBResults() {
    }

    public void retrieveDBResults() {
        if (!ConnectivityHelper.getInstance().isNetworkAvailable()) {
            // Note: remember to write some code for if network is unavailable
            return;
        }
        // Set up connection for app to talk to database via rest controller
        MessageService service = ServiceGenerator.createService(MessageService.class);

        // Retrieve all messages stored in database
        Call<List<Message>> callAsync = service.getAllMessages();
        callAsync.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                List<Message> allMessages = response.body();
                // NOTE: use allMessages.get([INDEX]).[ATTRIBUTE] to extract message data, as below
                assert allMessages != null;
                DBResults.getInstance().setMessages(allMessages);
            }
            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
                // Sends an empty list of messages to observers
                DBResults.getInstance().setMessages(new ArrayList<>());
            }
        });

        // Retrieve all banners stored in database
        Call<List<Banner>> bannerCallAsync = service.getAllBanners();
        bannerCallAsync.enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(@NonNull Call<List<Banner>> call, @NonNull Response<List<Banner>> response) {
                List<Banner> allBanners = response.body();
                assert allBanners != null;
                DBResults.getInstance().setBanners(allBanners);
            }

            @Override
            public void onFailure(@NonNull Call<List<Banner>> call, @NonNull Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
                // Sends an empty array list of banners to observers
                DBResults.getInstance().setBanners(new ArrayList<>());
            }
        });
    }

    public void registerObserver(DBObserver observer) {
        observers.add(observer);
        if (this.messages != null) {
            observer.onMessageSuccess(messages);
        }
        if (this.banners != null) {
            observer.onBannerSuccess(banners);
        }
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        for (DBObserver o : observers) {
            o.onMessageSuccess(messages);
        }
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
        for (DBObserver o : observers) {
            o.onBannerSuccess(banners);
        }
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    // Note: in theory, this should be safe as you register one GPSObserver and remove it every time
    // you switch between fragments that implement ApiCallback
    public void clearObservers() {
        this.observers.clear();
    }
}