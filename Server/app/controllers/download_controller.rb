class DownloadController < ActionController::Base

  def download_app
    user_agent = request.env['HTTP_USER_AGENT']
    device_name = detect_mobile_device(user_agent)
    return redirect_to "/TW-Conference-2012-release.apk" if device_name == "android"
    return redirect_to "itms-services://?action=download-manifest&url=http://awayday.thoughtworkers.org/AwayDay2012.plist" if device_name == "ios"
    return redirect_to "/"
  end

  def detect_mobile_device(user_agent)
    downcase_user_agent = user_agent.to_s.downcase
    return "android" if (downcase_user_agent.include?("android"))
    return "ios" if (downcase_user_agent.include?("ipad") || downcase_user_agent.include?("iphone") || downcase_user_agent.include?("ipod"))
  end
end