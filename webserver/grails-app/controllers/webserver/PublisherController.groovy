package webserver

class PublisherController {
	
	def restApiService
	def publisherService
	
 def publish() { 

		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		Products prod = Products.get(params.productId)
		
		def result = publisherService.publishItem(meliUser.token, prod, params.listing)
		
		redirect(controller: "product", action: "list")
		
	}
}
