class InsertDefaultEvent < ActiveRecord::Migration
  def up
    Event.create!
  end

  def down
    Event.delete_all
  end
end
