require "net/http"
require "digest/md5"
require "uri"

class SessionsController < ApplicationController

  http_basic_authenticate_with :name => "twer", :password => "r0ys1ngh4m", :except => :grouped_by_date


  def grouped_by_date
    render json: Session.grouped_by_date
  end

  # GET /sessions
  # GET /sessions.json
  def index
    @sessions = Session.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @sessions }
    end
  end

  # GET /sessions/1
  # GET /sessions/1.json
  def show
    @session = Session.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @session }
    end
  end

  # GET /sessions/new
  # GET /sessions/new.json
  def new
    @session = Session.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @session }
    end
  end

  # GET /sessions/1/edit
  def edit
    @session = Session.find(params[:id])
  end

  # POST /sessions
  # POST /sessions.json
  def create
    @session = Session.new(params[:session])

    respond_to do |format|
      if @session.save
        format.html { redirect_to @session, notice: 'Session was successfully created.' }
        format.json { render json: @session, status: :created, location: @session }
      else
        format.html { render action: "new" }
        format.json { render json: @session.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /sessions/1
  # PUT /sessions/1.json
  def update
    @session = Session.find(params[:id])
    p "==="
    p @session

    respond_to do |format|
      if @session.update_attributes(params[:session])
        push_response_message = sendUpdatedMessageToClient @session
        notice = "Session was successfully updated. <br/> //" + push_response_message
        format.html { redirect_to @session, notice: notice }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @session.errors, status: :unprocessable_entity }
      end
    end
  end

  def sendUpdatedMessageToClient(modified_session)
    uri = URI.parse "http://api.jpush.cn:8800/sendmsg/v2/sendmsg"

    send_no = Integer((DateTime.now - 11.days).strftime("%d%H%M%S"))
    receiver_type = 4 # broadcast to all users
    msg_type = 1 # message type is notification, not custom message
    master_key = "a3d0da68dc9d969fed910896"
    app_key = "1de28e61f1ec10e00c32bb5a"
    verification_code = generate_verification_code(send_no, receiver_type, "", master_key)
    reminder_message = generate_notification(modified_session)

    request = Net::HTTP::Post.new(uri.request_uri, initheader = {'Content-Type' =>'application/json'})
    request.set_form_data({"sendno" => send_no, "app_key" => app_key, "receiver_type" => receiver_type, "verification_code" => verification_code, "msg_type" => msg_type, "msg_content" => reminder_message, "platform" => "android,ios" })
    response = Net::HTTP.new(uri.host, uri.port).request(request)

    p "Response #{response.code}, #{response.message}, #{response.body}"
    return format_response_message(response)
  end

  def format_response_message(response)
    if response.code == "200"
      response_message = JSON.parse(response.body)
      if response_message["errcode"] == 0
        return "Send notification successfully"
      else
        return "Fail to send notification1:"+ response_message["errmsg"]
      end
    else
      return "Fail to send notification2:#{response.message}"
    end
  end

  def generate_notification(modified_session)
    n_content = "'" + modified_session[:title] + "'has been modified into " + modified_session[:location] + " at " + modified_session[:start].strftime("%Y%m%d %H:%M:%S")
    formatted_content = {"n_content" => n_content}.to_json
    p formatted_content
    return formatted_content
  end

  def generate_verification_code(send_no, receiver_type, receiver_value, app_key)
    return Digest::MD5.hexdigest(send_no.to_s + receiver_type.to_s + receiver_value + app_key)
  end

  # DELETE /sessions/1
  # DELETE /sessions/1.json
  def destroy
    @session = Session.find(params[:id])
    @session.destroy

    respond_to do |format|
      format.html { redirect_to sessions_url }
      format.json { head :no_content }
    end
  end
end
