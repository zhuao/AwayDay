package com.thoughtworks.mobile.awayday.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thoughtworks.mobile.awayday.R;
import com.thoughtworks.mobile.awayday.domain.Agenda;
import com.thoughtworks.mobile.awayday.domain.Session;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;

public class LessonTwoFragment extends Fragment {

    private ListView agendaListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lesson_two, container, false);
        agendaListView = ((ListView) fragmentView.findViewById(R.id.lesson_two_list_view));
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        InputStream inputStream = getResources().openRawResource(R.raw.agendas);
        final List<Agenda> agendas = Agenda.parseAgendas(new InputStreamReader(inputStream));
        agendaListView.setAdapter(new LessonTwoAdapter(agendas.get(0).getSessions()));
        agendaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isHidden = view.findViewById(R.id.session_description).getVisibility() == View.GONE;
                view.findViewById(R.id.session_speaker_layout).setVisibility(isHidden? View.VISIBLE : View.GONE);
                view.findViewById(R.id.session_location_layout).setVisibility(isHidden? View.VISIBLE : View.GONE);
                view.findViewById(R.id.session_description).setVisibility(isHidden? View.VISIBLE : View.GONE);
            }
        });

    }

    private static class LessonTwoAdapter extends BaseAdapter {
        public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("hh:mm");
        private final List<Session> sessionList;

        public LessonTwoAdapter(List<Session> sessionList) {
            this.sessionList = sessionList;
        }

        @Override
        public int getCount() {
            return sessionList.size();
        }

        @Override
        public Object getItem(int position) {
            return sessionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_view, parent, false);
                viewHolder.speakerView = ((TextView) convertView.findViewById(R.id.session_speaker));
                viewHolder.locationView = ((TextView) convertView.findViewById(R.id.session_location));
                viewHolder.descriptionView = ((TextView) convertView.findViewById(R.id.session_description));
                viewHolder.titleView = ((TextView) convertView.findViewById(R.id.session_title));
                viewHolder.durationView = ((TextView) convertView.findViewById(R.id.session_duration));
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            
            Session session = ((Session) getItem(position));
            viewHolder.speakerView.setText(session.sessionSpeaker);
            viewHolder.locationView.setText(session.sessionLocation);
            viewHolder.descriptionView.setText(session.sessionNote);
            viewHolder.titleView.setText(session.sessionTitle);
            viewHolder.durationView.setText(String.format("%1$s-%2$s", SIMPLE_DATE_FORMAT.format(session.sessionStartTime), SIMPLE_DATE_FORMAT.format(session.sessionEndTime)));
            return convertView;
        }

        private class ViewHolder {
            public TextView speakerView;
            public TextView locationView;
            public TextView descriptionView;
            public TextView titleView;
            public TextView durationView;
        }
    }
}
