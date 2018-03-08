package com.jonbott.knownspies.Activities.Details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonbott.knownspies.Activities.SecretDetails.SecretDetailsActivity;
import com.jonbott.knownspies.Helpers.Constants;
import com.jonbott.knownspies.Helpers.Helper;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.R;

import io.realm.Realm;

public class SpyDetailsActivity extends AppCompatActivity {

    private Realm realm = Realm.getDefaultInstance();

    private int spyId = -1;

    private ImageView profileImage;
    private TextView nameTextView;
    private TextView ageTextView;
    private TextView genderTextView;
    private ImageButton calculateButton;

    private SpyDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spy_details);
        setupUI();
        parseBundle();

    }

    public void configure(SpyDetailsPresenter spyDetailsPresenter) {
        this.presenter = spyDetailsPresenter;
        this.presenter.configureWithContext(this);
    }


    //region Helper Methods

    private void setupUI() {
        profileImage = (ImageView) findViewById(R.id.details_profile_image);
        nameTextView = (TextView) findViewById(R.id.details_name);
        ageTextView = (TextView) findViewById(R.id.details_age);
        genderTextView = (TextView) findViewById(R.id.details_gender);
        calculateButton = (ImageButton) findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(v -> gotoSecretDetails());
    }


    private void configureUIWith(SpyDetailsPresenter spyDetailsPresenter) {

        profileImage.setImageResource(presenter.imageId);
        nameTextView.setText(spyDetailsPresenter.name);
        ageTextView.setText(String.valueOf(spyDetailsPresenter.age));
        genderTextView.setText(spyDetailsPresenter.gender);


    }


    // dependency methods
    private void getPresenterFor(int spyId) {
        configure(new SpyDetailsPresenter(spyId));
    }

    private void parseBundle() {
        Bundle b = getIntent().getExtras();

        if (b != null)
            spyId = b.getInt(Constants.spyIdKey);
         getPresenterFor(spyId);


    }

    //endregion


    //region navigation

    private void gotoSecretDetails() {
        if (spyId == -1) return;

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.spyIdKey, presenter.spyId);

        Intent intent = new Intent(SpyDetailsActivity.this, SecretDetailsActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    //endregion
}
