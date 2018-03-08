package com.jonbott.knownspies.Activities.SecretDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jonbott.knownspies.Activities.SpyList.SpyListActivity;
import com.jonbott.knownspies.Helpers.Constants;
import com.jonbott.knownspies.Helpers.Threading;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.R;

import io.realm.Realm;

public class SecretDetailsActivity extends AppCompatActivity {


    ProgressBar progressBar;
    TextView crackingLabel;
    Button finishedButton;

    SecretDetailsPresenter presenter;
    int spyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_details);

        setupUI();
        parseBundle();

    }


    private void configure(SecretDetailsPresenter secretDetailsPresenter){
        this.presenter=secretDetailsPresenter;
        presenter.crackPassword(password->
        {
            progressBar.setVisibility(View.GONE);
            crackingLabel.setText(presenter.password);
        });
    }

    //region Helper Methods

    private void setupUI() {
        progressBar    = (ProgressBar) findViewById(R.id.secret_progress_bar);
        crackingLabel  = (TextView)    findViewById(R.id.secret_cracking_label);
        finishedButton = (Button)      findViewById(R.id.secret_finished_button);

        finishedButton.setOnClickListener(v -> finishedClicked() );

    }


    public void setupPresenter(int spyId){
        configure(new SecretDetailsPresenter(spyId));
    }


    private void parseBundle() {
        Bundle b = getIntent().getExtras();

        if(b != null)
            spyId = b.getInt(Constants.spyIdKey);
            setupPresenter(spyId);

    }

    //endregion

    //region User Interaction

    private void finishedClicked() {
        Intent intent = new Intent(this, SpyListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    //endregion

    //region Data loading

    //endregion


}
