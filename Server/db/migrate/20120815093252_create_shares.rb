class CreateShares < ActiveRecord::Migration
  def change
    create_table :shares do |t|
      t.string :session_id
      t.integer :timestamp
      t.string :device_id
      t.string :username
      t.text :share_text
      t.text :share_image

      t.timestamps
    end
  end
end
