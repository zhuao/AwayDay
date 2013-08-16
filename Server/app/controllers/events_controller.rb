class EventsController < ApplicationController

  http_basic_authenticate_with :name => "twer", :password => "r0ys1ngh4m", :except => :info

  def show
    @event = Event.find(1)

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @event }
    end
  end

  def edit
    @event = Event.find(1)
  end

  def update
    @event = Event.find(1)

    respond_to do |format|
      if @event.update_attributes(params[:event])
        format.html { redirect_to event_path, notice: 'Event was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @event.errors, status: :unprocessable_entity }
      end
    end
  end

  def info
    @event = Event.find(1)
    render json: @event.to_json(:only => [:introduction, :latitude, :longitude], :methods => [:photo_url])
  end

end
