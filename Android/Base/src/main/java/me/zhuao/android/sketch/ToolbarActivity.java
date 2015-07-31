package me.zhuao.android.sketch;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        FrameLayout content = (FrameLayout) findViewById(R.id.fragment_content);
        getLayoutInflater().inflate(layoutResID, content);
    }

    protected void inflateFragment(Fragment frag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, frag, frag.getClass().getName());
        transaction.commit();
    }


}
