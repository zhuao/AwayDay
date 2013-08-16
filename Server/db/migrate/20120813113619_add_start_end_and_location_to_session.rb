class AddStartEndAndLocationToSession < ActiveRecord::Migration
  def change
    add_column :sessions, :start, :timestamp
    add_column :sessions, :end, :timestamp
    add_column :sessions, :location, :string
  end
end
