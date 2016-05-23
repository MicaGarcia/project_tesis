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

    def list() { 
		if(!session.user) {
			redirect (controller: "home", action:"login")
			return false
		}
		
		MeliUsers meliUser = MeliUsers.findByUser(session.user)						
		def listOrders = orderService.getOrders(meliUser)		
		render (view:"list", model: [listOrders:listOrders])
		
		
	}
}
