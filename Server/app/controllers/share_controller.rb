require "base64"

class ShareController <  ApplicationController
  include ApplicationHelper
  include Weibo
  include FileHelper
  def create
    session_id = params[:session_id]
    timestamp = params[:timestamp]
    device_id = params[:device_id]
    username = params[:username]
    share_text = params[:share_text]
    share_image = params[:share_image]
    logger.info "creating share #{timestamp} for session_id #{session_id}"
    p session_id+" "+device_id+" "+username+" "+share_text
    sentToWeibo(username+" "+share_text,getImageFile(share_image))
    share = Share.new params[:share]
    if share.save
      render status: :created, json: {
        timestamp: timestamp,
        message: "Successful"
      }
    else
      render status: :unprocessable_entity, json: {
        timestamp: timestamp,
        message: share.errors
      }
    end
  end
end