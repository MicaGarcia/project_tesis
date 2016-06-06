package webserver

class PublisherController {

	def restApiService
	def publisherService

	def publish() {

		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		Products prod = Products.get(params.productId)

		publisherService.defineAction(meliUser, prod, params.listing)

		redirect(controller: "product", action: "list")
	}

	def deleteItem() {
		
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		Products prod = Products.get(params.productId)
		
		publisherService.deleteItem(meliUser, prod)
	
		redirect(controller: "product", action:"list")
	}
	
	def changeStatus() {
		
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		Products prod = Products.get(params.productId)
		
		publisherService.changeStatus(meliUser, prod)
	
		redirect(controller: "product", action:"list")
		
	}
	
	def closeItem() {
		
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		Products prod = Products.get(params.productId)
		
		publisherService.closeItem(meliUser, prod)
	
		redirect(controller: "product", action:"list")
		
	}
	
	def relist() {
		
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		Products prod = Products.get(params.productId)
		
		publisherService.relist(meliUser, prod)
	
		redirect(controller: "product", action:"list")
		
	}
}
