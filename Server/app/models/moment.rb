class Moment < ActiveRecord::Base
  attr_accessible :device_id, :image_content, :text_content, :timestamp, :user_name, :moment
  validates_presence_of :device_id
end