package com.jonbott.knownspies.ModelLayer;

import com.jonbott.knownspies.Helpers.Threading;
import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.DataLayer;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.ModelLayer.Enums.Source;
import com.jonbott.knownspies.ModelLayer.Network.NetworkLayer;
import com.jonbott.knownspies.ModelLayer.Translation.SpyTranslator;
import com.jonbott.knownspies.ModelLayer.Translation.TranslationLayer;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.realm.Realm;

/**
 * Created by arjun_mu on 3/8/2018.
 */

public class ModelLayer
{
    private NetworkLayer networkLayer = new NetworkLayer();
    private DataLayer dataLayer = new DataLayer();
    private TranslationLayer translationLayer = new TranslationLayer();

    public void loadData(Consumer<List<SpyDTO>> onNewResults, Consumer<Source> notifyDataReceived) {
        SpyTranslator translator = translationLayer.translatorFor(SpyDTO.dtoType);

        try {
            dataLayer.loadSpiesFromLocal(translator::translate, onNewResults);
            notifyDataReceived.accept(Source.local);
        } catch (Exception e) {
            e.printStackTrace();
        }

        networkLayer.loadJson(json -> {
            notifyDataReceived.accept(Source.network);
            persistJson(json, ()->dataLayer.loadSpiesFromLocal(translator::translate, onNewResults));
        });


    }

    public SpyDTO spyForId(int spyId) {
        Spy spy = dataLayer.spyForId(spyId);
        SpyDTO spyDTO = translationLayer.translate(spy);
        return spyDTO;
    }


    private void persistJson(String json, Action finished) {
        List<SpyDTO> dtos = translationLayer.convertJson(json);

        Threading.async(()->{
            dataLayer.clearSpies(() -> {
                dtos.forEach(dto -> dto.initialize());

                SpyTranslator translator = translationLayer.translatorFor(SpyDTO.dtoType);
                dataLayer.persistDTOs(dtos, translator::translate);

                Threading.dispatchMain(() -> finished.run());
            });

            return true;
        });

    }
}
