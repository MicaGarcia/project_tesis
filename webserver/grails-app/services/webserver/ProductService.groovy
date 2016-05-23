package webserver

import grails.transaction.Transactional

@Transactional
class ProductService {

	def getAllProducts(userId) {

		def listProducts
		try {
			listProducts = Products.findAllByUserId(userId)
		}
		catch (e) {
			println "Error getting all products - ${e}"
		}
		return listProducts
	}

	def getById(productId) {

		Products product
		try {
			product = Products.get(productId)
		}
		catch (e) {
			println "Error getting product ${productId} - ${e}"
		}
		return product
	}

	def create(params, user) {

		def result
		def product

		try {
			product = new Products(params)
			//como manejar las imagenes
			//			def imagen = request.getFile('imagenFile')
			//			if (imagen.originalFilename) {
			//				def imageDir = servletContext.getRealPath("/") + 'images/properties'
			//				imagen.transferTo(new File(imageDir,imagen.originalFilename))
			//			}
			//product.picture = imagen?.originalFilename ?: "noimage.jpg"

			product.currency = "ARS"
			product.warranty = "Garantia de Test"
			product.userId = user.id
			product.image1 = "http://mla-s1-p.mlstatic.com/822711-MLA20604428947_022016-F.jpg"
			product.image2 = "http://mla-s1-p.mlstatic.com/822711-MLA20604428947_022016-F.jpg"
			product.image3 = "http://mla-s1-p.mlstatic.com/822711-MLA20604428947_022016-F.jpg"
			product.image4 = "http://mla-s1-p.mlstatic.com/822711-MLA20604428947_022016-F.jpg"
			product.image5 = "http://mla-s1-p.mlstatic.com/822711-MLA20604428947_022016-F.jpg"
			product.image6 = "http://mla-s1-p.mlstatic.com/822711-MLA20604428947_022016-F.jpg"
			product.save(flush:true,failOnError:true)

			result = true
		}
		catch (Exception e) {
			println e
			result = false
		}

		return result
	}

	def update() {
	}

	def delete(productId) {
		try {
			Products p = Products.get(productId)
			p.delete()
		}
		catch(Exception e) {
			println e
		}
	}
	
}
