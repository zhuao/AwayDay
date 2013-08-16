class MomentController < ApplicationController
  def create
    timestamp = params[:timestamp]
    device_id = params[:device_id]
    logger.info "creating moment at #{timestamp} for device_id #{device_id}"
    moment = Moment.new params[:moment]
    if moment.save
      render status: :created, json: {
        result: 'done',
        timestamp: timestamp
      }
    else
      render status: :unprocessable_entity, json: {
        result: 'fail',
        timestamp: timestamp,
        errors: moment.errors
      }
    end
  end

  def destroy
    timestamp = params[:timestamp]
    device_id = params[:device_id]
    count = Moment.delete_all ['device_id = ? and timestamp = ?', device_id, timestamp]
    logger.info "destroyed #{count} moments at #{timestamp} for device_id #{device_id}"
    render json: {
      result: 'done',
      timestamp: timestamp
    }
  end
end