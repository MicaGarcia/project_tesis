package webserver

class OrderController {

	def orderService

	def index() {

		if(!session.user) {
			redirect (controller: "home", action:"login")
			return false
		}
		else {
			redirect(controller:"orders", action : "list")
		}
	}
	
	def updateList() {
		
		def listOrders 
		try {
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		listOrders = orderService.updateOrders(meliUser)
		}
		catch (Exception e) {
			println "Exception trying to get Orders List in Update - ${e}"
		}

		println listOrders
		render(view:"list", model:[listOrders: listOrders])
	}

	def list() {

		def listOrders
		if(!session.user) {
			redirect (controller: "home", action:"login")
			return false
		}

		try {

			MeliUsers meliUser = MeliUsers.findByUser(session.user)
			listOrders = orderService.getOrders(meliUser)
			println listOrders
		}
		catch (Exception e) {
			println "Exception trying to get Orders List - ${e}"
		}
		render (view:"list", model: [listOrders:listOrders])
	}
	
}
