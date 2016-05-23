package webserver

class FeedbackController {

	//def RestApiService
	
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
		
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		def token = meliUser.token
		def bodyContent
		
		def orderId = params.orderId
		Orders order = Orders.get(orderId)
				
		//def fulfilled = "false"
		def rating = params.feedback
		def reason = params.reason
		def message = params.feedText
		def errorMens
		
		//if (order.status == "paid")
		if (order.status)
		{
			
//			if (!order.shipping == "to_be_agreed") {
//
//				if (order.shipping == "shipped") {
//
//					fulfilled = "true"
//				}
//			}
			
			 
		

		if (rating == "negative") {
				
		bodyContent = [
								
			"rating": rating,
			"reason": reason,
			"fulfilled": "false",
			"message": message
											
			]
		}
		
		else {
			
			bodyContent = [
				
				"rating": rating,
				"fulfilled": "true",
				"message": message
							
			]
		
		}
		
		try {
			
		def result = RestApiService.post("/orders/"+order.orderId+"/feedback?access_token="+token, bodyContent)
		
		if (result.success == "true") {
		order.sellerFeed = result.data.fulfilled
		order.save()
		
		}
		
		else {
			
			 errorMens = result.error
			}
		}
		
		catch (Exception e){
			
			def error = e.getMessage()
			println "ERROR :"+error
			redirect(controller: "orders", action:"list",model: [error:error])
			
			}
		}

		redirect(controller: "orders", action:"list", model:[error : errorMens])
	}
}
