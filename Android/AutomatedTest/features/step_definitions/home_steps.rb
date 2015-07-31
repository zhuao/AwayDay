require 'calabash-android/calabash_steps'

When(/^I navigate to "(.*?)"$/) do |value|
  @home_page.choose_navigation_button(value)
end