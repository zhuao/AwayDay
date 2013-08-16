class Message < ActiveRecord::Base
  attr_accessible :content


  def created_time
    created_at.strftime("%Y-%m-%d %H:%M:%S%z")
  end
end
