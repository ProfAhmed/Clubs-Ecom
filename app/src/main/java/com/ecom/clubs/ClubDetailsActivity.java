package com.ecom.clubs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecom.clubs.events.EventClub;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClubDetailsActivity extends AppCompatActivity {

    @BindView(R.id.ivClubImage)
    ImageView ivClubImage;

    @BindView(R.id.tvClubName)
    TextView tvClubName;

    @BindView(R.id.tvClubAddress)
    TextView tvClubAddress;

    @BindView(R.id.tvClubPhone)
    TextView tvClubPhone;

    @BindView(R.id.btnDirections)
    Button btnDirections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);
        ButterKnife.bind(this);

        btnDirections.setOnClickListener(v -> {
            Intent intent = new Intent(this, DirectionsActivity.class);
            startActivity(intent);
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(EventClub event) {

        Toast.makeText(this, event.getClubModel().toString(),
                Toast.LENGTH_SHORT).show();
        EventBus.getDefault().removeStickyEvent(event.toString()); // don't forget to remove the sticky event if youre done with it
        tvClubName.setText(event.getClubModel().getName());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }
}
