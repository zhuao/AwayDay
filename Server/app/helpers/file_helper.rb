module FileHelper
  include ApplicationHelper
  def getProperty(key)
    token = ""
    tokenFile = File.new(TokenFileName)
    tokenFile.each_line do |line|
      if line.include? key then
        token = line.split("=")[1].strip
      end
    end
    return token
  end

  def getImageFile(base64String)
    if base64String != nil then
      File.open("temp.jpeg", "w:ASCII-8BIT:UTF-8") do |file|
        file.puts Base64.decode64(base64String)
      end
      return File.new("temp.jpeg")
    end
    return nil
  end

  def updateTokenFile(token,expire)
    p "update token file..."
    tokenFile = File.new(TokenFileName,"w+")
    tokenFile.puts "api_key=3463139052"
    tokenFile.puts "api_secret=28eda12f668f38aa5bad6053836eee57"
    tokenFile.puts "redirect_uri=http://127.0.0.1:4567/callback"
    tokenFile.puts "acces_token="+token
    tokenFile.puts "expires_at="+expire.to_s
    tokenFile.close
  end

end