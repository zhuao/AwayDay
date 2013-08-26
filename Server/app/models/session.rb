class Session < ActiveRecord::Base
  attr_accessible :date, :description, :title, :start, :end, :speaker, :location

  def self.grouped_by_date
    Session.group('date').select('date').map do |s|
      sessions = Session.where('date = ?', s.date).order('start')
      { agenda_date: s.date,
        agenda_sessions: sessions.map {|ds| ds.to_json_session }
      }
    end
  end

  def to_json_session
    { session_description: description,
      session_title: title,
      session_speaker: speaker,
      session_start: start.strftime("%Y-%m-%dT%H:%M:%S%z"),
      session_end: self.end.strftime("%Y-%m-%dT%H:%M:%S%z"),
      session_location: location,
      session_id: id
    }
  end
end
