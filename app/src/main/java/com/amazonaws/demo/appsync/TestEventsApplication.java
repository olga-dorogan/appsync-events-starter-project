package com.amazonaws.demo.appsync;

import android.app.Application;
import android.util.Log;

import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

public class TestEventsApplication extends Application {
    private static final String TAG = "+++++++";

    private AppSyncSubscriptionCall<NewCommentOnEventSubscription.Data> subscriptionWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        NewCommentOnEventSubscription subscription = NewCommentOnEventSubscription.builder()
                .eventId("2a4dd087-73bf-46d7-b3de-765a428a973c")
                .build();

        subscriptionWatcher = ClientFactory.getInstance(this.getApplicationContext()).subscribe(subscription);
        subscriptionWatcher.execute(subscriptionCallback);
        Log.e(TAG, "===== Subscription started from application class");
    }

    private AppSyncSubscriptionCall.Callback<NewCommentOnEventSubscription.Data> subscriptionCallback =
            new AppSyncSubscriptionCall.Callback<NewCommentOnEventSubscription.Data>() {
                @Override
                public void onResponse(final @Nonnull Response<NewCommentOnEventSubscription.Data> response) {
                    Log.e(TAG, "===== Subscription response errors" + response.errors());
                    Log.e(TAG, "===== Subscription response data " + response.data());
                }

                @Override
                public void onFailure(final @Nonnull ApolloException e) {
                    Log.e(TAG, "===== Subscription failure", e);
                }

                @Override
                public void onCompleted() {
                    Log.d(TAG, "===== Subscription completed");
                }
            };
}
