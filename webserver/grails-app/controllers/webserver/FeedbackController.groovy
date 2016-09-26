package webserver

class FeedbackController {
	
	def feedbackService
	
	def index() {
		if(!session.user) {
			redirect (controller: "home", action:"login")
			return false
		}
		else {			
			redirect(controller:"home", action : "configuration")
		}				
	}
	
	def giveFeedback() {
		
		try {
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		def res = feedbackService.giveFeedback(meliUser, params) 
			
		}
		catch(Exception e) {
			println e
		}
		
		redirect(controller: "order", action:"list")
		
	}
}
