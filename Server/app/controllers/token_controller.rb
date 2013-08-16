class TokenController < ApplicationController
  include FileHelper
  def refresh
    p "refresh token..."
    client = createClient
    token = client.get_token_from_hash({:access_token => getProperty("access_token"), :expires_at => getProperty("expires_at").to_i})
    unless token.validated? then
      redirect_to createClient.authorize_url
      return
    end
    render status: :created, json: {
        timestamp: Time.now.getutc,
        message: "The token is valid!"
      }
  end

  def callback
    p "get callback..."
    access_token = createClient.auth_code.get_token(params[:code].to_s)
    p "*" * 80 + "validated"
    p access_token.inspect
    updateTokenFile(access_token.token,access_token.expires_at)
    redirect_to '/refresh_token'
  end

  def createClient
    WeiboOAuth2::Config.api_key = getProperty("api_key")
    WeiboOAuth2::Config.api_secret = getProperty("api_secret")
    WeiboOAuth2::Config.redirect_uri = getProperty("redirect_uri")
    WeiboOAuth2::Client.new
  end
  private:createClient
end
