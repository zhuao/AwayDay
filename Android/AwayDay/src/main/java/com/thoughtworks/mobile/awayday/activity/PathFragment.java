package com.thoughtworks.mobile.awayday.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Path;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.factory.PathItemBuilder;
import com.thoughtworks.mobile.awayday.listeners.OnPathAddActionListener;
import com.thoughtworks.mobile.awayday.presenter.PathPresenter;
import com.thoughtworks.mobile.awayday.screen.PathScreen;
import com.thoughtworks.mobile.awayday.service.implement.FetchTrackServiceImpl;
import com.thoughtworks.mobile.awayday.storage.BeanContext;

public class PathFragment extends Fragment implements OnPathAddActionListener {

    private PathScreen pathScreen;
    private PathPresenter pathPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.path_page, container, false);
        pathScreen = (PathScreen) rootView.findViewById(R.id.path_screen);
        initPath();
        return rootView;
    }

    private void initPath() {
        PathItemBuilder localPathItemBuilder = new PathItemBuilder(getActivity());
        this.pathScreen.initComponent(localPathItemBuilder);
        this.pathScreen.setUserName(Settings.getSettings().getUserName());
        this.pathScreen.setAddButtonClickedListener(this);
        this.pathPresenter = new PathPresenter(pathScreen);
        Settings.getSettings().addListener(this.pathPresenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Path) BeanContext.getInstance().getBean(Path.class)).addPathDataChangedListener(pathPresenter);
        this.pathPresenter.loadFootprints(new FetchTrackServiceImpl());
    }

    @Override
    public void onStop() {
        super.onStop();
        ((Path) BeanContext.getInstance().getBean(Path.class)).removePathDataChangedListener(pathPresenter);
    }

    public void onPathAddAction() {
        startShareActivity(new Intent());
    }

    private void startShareActivity(Intent paramIntent) {
        paramIntent.setClass(getActivity(), ShareActivity.class);
        startActivity(paramIntent);
    }
}
