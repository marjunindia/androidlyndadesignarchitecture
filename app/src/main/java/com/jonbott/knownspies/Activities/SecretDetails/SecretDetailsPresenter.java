package com.jonbott.knownspies.Activities.SecretDetails;

import android.view.View;

import com.jonbott.knownspies.Helpers.Threading;
import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.DataLayer;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.ModelLayer.ModelLayer;

import java.util.function.Consumer;

import io.realm.Realm;

/**
 * Created by arjun_mu on 3/8/2018.
 */

class SecretDetailsPresenter {

    ModelLayer modelLayer = new ModelLayer();

    private SpyDTO spy;
    public String password;

    public SecretDetailsPresenter(int spyId) {
        spy = modelLayer.spyForId(spyId);

        password = spy.password;
    }

    public void crackPassword(io.reactivex.functions.Consumer<String> finished) {
        Threading.async(()-> {
            //fake processing work
            Thread.sleep(2000);
            return true;
        }, success -> {
            finished.accept(password);
        });
    }



}
