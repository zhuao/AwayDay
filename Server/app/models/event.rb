class Event < ActiveRecord::Base
  attr_accessible :introduction, :latitude, :longitude, :photo
  has_attached_file :photo

  def photo_url
    photo.url
  end
end
