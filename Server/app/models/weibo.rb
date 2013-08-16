require "weibo_2"

module  Weibo
  include FileHelper
  
  def sentToWeibo(text,image)
    p "sending to weibo..."
    WeiboOAuth2::Config.api_key = getProperty("api_key")
    WeiboOAuth2::Config.api_secret = getProperty("api_secret")
    WeiboOAuth2::Config.redirect_uri = getProperty("redirect_uri")
    client = WeiboOAuth2::Client.new
    token = client.get_token_from_hash({:access_token => getProperty("access_token"), :expires_at => getProperty("expires_at").to_i})
    statuses = client.statuses 
    if image == nil then
      statuses.update("#AwayDay2012#"+text)
    else
      statuses.upload("#AwayDay2012#"+text,image)  
      File.delete(image)
    end
    
    p "sending to weibo finished"
  end
end
