package com.jonbott.knownspies.Activities.Details;

import android.content.Context;

import com.jonbott.knownspies.Helpers.Helper;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;

import io.realm.Realm;

/**
 * Created by arjun_mu on 3/8/2018.
 */

class SpyDetailsPresenter {

    public int spyId;
    String age;
    String name;
    String gender;
    String imageName;
    int imageId;
    private Realm realm = Realm.getDefaultInstance();
    private Context mContext;


    public SpyDetailsPresenter(int spyId) {
        this.spyId=spyId;
        Spy spy=getSpy(spyId);
        age=String.valueOf(spy.age);
        name=spy.name;
        gender=spy.gender;
        imageName=spy.imageName;
    }

    public void configureWithContext(SpyDetailsActivity context) {
        this.mContext=context;
        imageId= Helper.resourceIdWith(context,imageName);
    }

    //region Data loading

    private Spy getSpy(int id) {
        Spy tempSpy = realm.where(Spy.class).equalTo("id", id).findFirst();
        return realm.copyFromRealm(tempSpy);
    }

    //endregion
}
