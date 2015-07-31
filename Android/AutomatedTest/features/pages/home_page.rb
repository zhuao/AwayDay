require 'calabash-android/abase'
require 'calabash-android/calabash_steps'

class HomePage < Calabash::ABase
  def choose_navigation_button(button_name)
    touch "android.widget.Button marked:'#{button_name}'"
  end

end
