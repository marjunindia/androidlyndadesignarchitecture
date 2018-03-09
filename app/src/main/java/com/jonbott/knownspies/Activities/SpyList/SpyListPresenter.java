package com.jonbott.knownspies.Activities.SpyList;


import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.ModelLayer.Enums.Source;
import com.jonbott.knownspies.ModelLayer.ModelLayer;
import java.util.List;
import io.reactivex.functions.Consumer;
import io.realm.Realm;


/**
 * Created by arjun_mu on 3/8/2018.
 */

public class SpyListPresenter {
    private static final String TAG = "SpyListPresenter";

    ModelLayer modelLayer = new ModelLayer();


    private Realm realm = Realm.getDefaultInstance();

    //region Presenter Methods

    public void loadData(Consumer<List<SpyDTO>> onNewResults, Consumer<Source> notifyDataReceived) {
        modelLayer.loadData(onNewResults, notifyDataReceived);
    }

    //endregion

}
