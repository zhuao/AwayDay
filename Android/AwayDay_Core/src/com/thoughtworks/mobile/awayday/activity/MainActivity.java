package com.thoughtworks.mobile.awayday.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.components.NavigationMenu;
import com.thoughtworks.mobile.awayday.domain.Footprint;
import com.thoughtworks.mobile.awayday.domain.Path;
import com.thoughtworks.mobile.awayday.domain.Session;
import com.thoughtworks.mobile.awayday.domain.Settings;
import com.thoughtworks.mobile.awayday.factory.AgendaItemBuilder;
import com.thoughtworks.mobile.awayday.factory.BitmapCacheManager;
import com.thoughtworks.mobile.awayday.factory.PathItemBuilder;
import com.thoughtworks.mobile.awayday.factory.TaskProvider;
import com.thoughtworks.mobile.awayday.helper.RemindAlarmHelper;
import com.thoughtworks.mobile.awayday.helper.ScreenSwitchHelper;
import com.thoughtworks.mobile.awayday.listeners.AgendaItemActionClickedListener;
import com.thoughtworks.mobile.awayday.listeners.OnPathAddActionListener;
import com.thoughtworks.mobile.awayday.listeners.OnRemindSetListener;
import com.thoughtworks.mobile.awayday.listeners.OnSessionJoinedListener;
import com.thoughtworks.mobile.awayday.presenter.AgendaPresenter;
import com.thoughtworks.mobile.awayday.presenter.PathPresenter;
import com.thoughtworks.mobile.awayday.presenter.SettingsPresenter;
import com.thoughtworks.mobile.awayday.screen.AgendaScreen;
import com.thoughtworks.mobile.awayday.screen.PathScreen;
import com.thoughtworks.mobile.awayday.screen.SettingScreen;
import com.thoughtworks.mobile.awayday.service.JoinService;
import com.thoughtworks.mobile.awayday.service.implement.FetchTrackServiceImpl;
import com.thoughtworks.mobile.awayday.storage.BeanContext;
import com.thoughtworks.mobile.awayday.storage.LocalStorage;

public class MainActivity extends Activity implements AgendaItemActionClickedListener, OnPathAddActionListener {
    private AgendaPresenter agendaPresenter;
    private AgendaScreen agendaScreen;
    private NavigationMenu navigationMenu;
    private OnRemindSetListener onRemindSetListener;
    private PathPresenter pathPresenter;
    private PathScreen pathScreen;
    private ScreenSwitchHelper screenSwitchHelper;
    private SettingScreen settingScreen;

    private void cancelRemind(Session paramSession) {
        paramSession.hasReminder = false;
        ((LocalStorage) BeanContext.getInstance().getBean(LocalStorage.class)).reminderSession(paramSession.sessionId, false);
        RemindAlarmHelper.getInstance(this).cancelRemind(paramSession.sessionId);
        setAgendaItemRemindActionImage(paramSession.hasReminder);
    }

    private void doSetRemind(OnRemindSetListener paramOnRemindSetListener, Session paramSession) {
        this.onRemindSetListener = paramOnRemindSetListener;
        Intent localIntent = new Intent();
        localIntent.putExtra("session", paramSession);
        localIntent.setClass(this, ReminderActivity.class);
        startActivityForResult(localIntent, 123);
    }

    private void initAgenda() {
        this.agendaPresenter = new AgendaPresenter(this.agendaScreen);
        AgendaItemBuilder localAgendaItemBuilder = new AgendaItemBuilder(this);
        localAgendaItemBuilder.setAgendaItemActionClickedListener(this);
        localAgendaItemBuilder.setAgenItemViewStateRecorder(this.agendaPresenter);
        this.agendaScreen.initComponent(localAgendaItemBuilder, this.agendaPresenter);
    }

    private void initComponent() {
        initAgenda();
        initNavigation();
        initPath();
        initSettings();
    }

    private void initNavigation() {
        this.screenSwitchHelper = new ScreenSwitchHelper(this.agendaScreen, this.pathScreen, this.settingScreen);
        this.navigationMenu.setScreenSwitchHelper(this.screenSwitchHelper);
    }

    private void initPath() {
        PathItemBuilder localPathItemBuilder = new PathItemBuilder(this);
        this.pathScreen.initComponent(localPathItemBuilder);
        this.pathScreen.setUserName(Settings.getSettings().getUserName());
        this.pathScreen.setAddButtonClickedListener(this);
        this.pathPresenter = new PathPresenter(this.pathScreen);
        Settings.getSettings().addListener(this.pathPresenter);
        ((Path) BeanContext.getInstance().getBean(Path.class)).addPathDataChangedListener(this.pathPresenter);
        this.pathPresenter.loadFootprints(new FetchTrackServiceImpl());
    }

    private void initSettings() {
        SettingsPresenter localSettingsPresenter = new SettingsPresenter(this.settingScreen, this.navigationMenu);
        localSettingsPresenter.initialize(Settings.getSettings().getUserName());
        this.settingScreen.setOnSettingsModifyActionListener(localSettingsPresenter);
    }

    private void initUI() {
        setContentView(R.layout.main_layout);
        this.agendaScreen = ((AgendaScreen) findViewById(R.id.agenda_screen));
        this.pathScreen = ((PathScreen) findViewById(R.id.path_screen));
        this.settingScreen = ((SettingScreen) findViewById(R.id.settings_screen));
        this.navigationMenu = ((NavigationMenu) findViewById(R.id.navigation_menu));
    }

    private void setAgendaItemRemindActionImage(boolean paramBoolean) {
        if (this.onRemindSetListener == null)
            return;
        this.onRemindSetListener.onRemindSet(paramBoolean);
    }

    private void startFetchAgendas() {
        this.agendaScreen.startFetchAgendas();
    }

    private void startShareActivity(Intent paramIntent) {
        paramIntent.setClass(this, ShareActivity.class);
        startActivity(paramIntent);
        finish();
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        if ((paramInt1 == 123) && (paramInt2 == -1)) {
            boolean bool = paramIntent.getBooleanExtra("setResult", false);
            this.agendaPresenter.setRemindForSession(paramIntent.getIntExtra("sessionId", 0), bool);
            setAgendaItemRemindActionImage(bool);
        }
    }

    public void onBackPressed() {
        if (this.navigationMenu.isInterceptKeyboardBack()) {
            this.navigationMenu.onKeyboardBack();
            return;
        }
        super.onBackPressed();
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        initUI();
    }

    public void onJoinActionClicked(OnSessionJoinedListener paramOnSessionJoinedListener, Session paramSession) {
        TaskProvider.createJoinSessionTask(paramOnSessionJoinedListener, (JoinService) BeanContext.getInstance().getBean(JoinService.class)).execute(new Session[]{paramSession});
    }

    public void onLowMemory() {
        super.onLowMemory();
        BitmapCacheManager.getInstance().clearCache();
    }

    public void onPathAddAction() {
        startShareActivity(new Intent());
    }

    protected void onPostCreate(Bundle paramBundle) {
        super.onPostCreate(paramBundle);
        initComponent();
        startFetchAgendas();
    }

    public void onRemindActionClicked(OnRemindSetListener paramOnRemindSetListener, Session paramSession) {
        if (paramSession.hasReminder) {
            cancelRemind(paramSession);
            return;
        }
        doSetRemind(paramOnRemindSetListener, paramSession);
    }

    public void onShareActionClicked(Session paramSession) {
        Intent localIntent = new Intent();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramSession.sessionTitle;
        localIntent.putExtra("share", new Footprint(String.format("[%s]", arrayOfObject), paramSession.sessionId));
        startShareActivity(localIntent);
    }
}