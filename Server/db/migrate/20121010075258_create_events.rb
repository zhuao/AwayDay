class CreateEvents < ActiveRecord::Migration
  def change
    create_table :events do |t|
      t.text :introduction
      t.float :latitude
      t.float :longitude

      t.timestamps
    end
  end
end
