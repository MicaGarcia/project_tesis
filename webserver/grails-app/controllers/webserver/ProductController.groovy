package webserver

class ProductController {

	def productService
	def publisherService

	def index() {
		if(!session.user) {
			redirect (controller: "home", action:"login")
			return false
		}
		else {
			redirect(controller:"product", action : "list")
		}
	}


	def list() {
		if(!session.user) {
			redirect (controller: "home", action:"login")
			return false
		}

		def listProducts = productService.getAllProducts(session.user.id)
		def listingType = selectListing()

		render(view:"list", model:[listProducts: listProducts, listingType:listingType])
	}

	def details() {
		Products product = productService.getById(params.id)
		render(view:"details", model:[product: product])
	}


	def create() {
		if(!session.user) {
			redirect (controller: "home", action:"login")
			return false
		}
	}

	def save() {

		try {
			def prod = productService.create(params, session.user)
			redirect (action: "list")
		}
		catch(Exception e) {
			render(view: "create")
		}
	}

	def deleteProduct() {
		def result = productService.delete(params.id)
		redirect(action:"list")
	}

	def edit() {

		def product = Products.get(params.id)
		
		if (!product) {
			redirect(action: "list")
			return
		}
		[product: product]
	}


	def updateProduct() {

		try {
			def result = productService.update(params)
			println params.id

			if (result) {
				flash.message = ""
				redirect (action: "details", id: params.id)
			}
			else {
				flash.message = ""
				redirect (action: "edit", id: params.id)
			}
		}
		catch(e){
			println e
			redirect(action: "details", id: params.id)
		}
	}



	def selectListing() {

		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		def custId = meliUser.custId
		def token = meliUser.token

		def availableListings = publisherService.checkListingType(custId, token)
	}

	def updateList() {
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		def res = publisherService.updateItems(meliUser)

		def listProducts = productService.getAllProducts(session.user.id)
		def listingType = selectListing()

		render(view:"list", model:[listProducts: listProducts, listingType:listingType])
	}
}
