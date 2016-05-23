package webserver

class NotificationController {

	def notificationService
	static allowedMethods = [index:'POST']

	def index() {

		def JSONObject

		try{
			JSONObject  = request.JSON;
			render(status: 200, text: 'OK.')			
			notificationService.saveNotification(JSONObject)
		}
		catch(Exception e){
			render(status: 503, text: 'Failed to do stuff.')
			println "error"+e.getMessage()
		}
	}

	def getNotification() {			
		notificationService.processNotifications(session.user)
		redirect(controller:"home", action:"configuration")
	}

}
