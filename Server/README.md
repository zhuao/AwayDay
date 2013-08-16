AwayDay2012_Server
==================

The server side of the AwayDay2012

To create sessions:

	rails console
	
    Session.create date: Date.today, title: 'introduction to rails', description: '', start: Date.today + 8.hours, end: Date.today + 9.hours, speaker: 'nick zhang', location: 'green room'

    Session.create date: Date.today, title: 'introduction to clojure', description: '', start: Date.today + 9.hours, end: Date.today + 10.hours, speaker: 'li yanhui', location: 'green room'

	session = Session.new
	session.date = Date.today
	session.title = 'introduction to clojure'
	session.description = ''
	session.start = Date.today + 9.hours
	session.end = Date.today + 10.hours
	session.speaker = 'li yanhui'
	session.location = 'green room'
	session.save

	session.destroy
	
	session = Session.find_by_title 'introduction to clojure'
	session.description = 'something about clojure'
	session.save
	
	Session.delete_all

To test the various entry points:

# Sessions

    curl http://localhost:3000/sessions_grouped_by_date

# Moments

    curl -H 'Content-Type: application/json' -d '{"device_id":"test_device_id","image_content":"the_image_base64_string","text_content":"the text content","timestamp":"222222222","user_name":"xuehai"}'  http://localhost:3000/moment

    curl -H 'Content-Type: application/json' -d '{"device_id":"test_device_id","timestamp":"222222222"}' -X DELETE http://localhost:3000/moment

# Share

	curl -H 'Content-Type: application/json' -d '{"session_id":"1234567","share_image":"the base64 string of the image file","device_id":"test_device_id","share_text":"This is a user share","username":"xuehai","timestamp":"1111111111"}'  http://localhost:3000/share