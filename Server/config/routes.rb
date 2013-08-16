AwayDay2012Rails::Application.routes.draw do
  resources :sessions
  resources :messages

  get 'sessions_grouped_by_date' => 'sessions#grouped_by_date'

  get 'event' => 'events#show'
  get 'event/edit' => 'events#edit'
  put 'event' => 'events#update'
  get 'event_info' => 'events#info'

  post 'moment' => 'moment#create'
  delete 'moment' => 'moment#destroy'
  post 'share' => 'share#create'
  get 'refresh_token' => 'token#refresh'
  get '/callback' => 'token#callback'
  get 'notification' => 'messages#notify'
end