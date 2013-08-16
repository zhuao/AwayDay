class CreateMoments < ActiveRecord::Migration
  def change
    create_table :moments do |t|
      t.string :device_id
      t.string :user_name
      t.text :image_content
      t.text :text_content
      t.integer :timestamp

      t.timestamps
    end
  end
end
