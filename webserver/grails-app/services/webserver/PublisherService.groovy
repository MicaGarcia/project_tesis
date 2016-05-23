package webserver

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class PublisherService {

	def restApiService

	def publishItem(token, prod, listing) {

		if(prod && token && listing) {

			def bodyContent
			def categoryId = selectCategory(prod)
			def modeShipping = checkShipping(categoryId)

			if (prod.stock > 0) {

				if (listing == "gold_special") {

					def images = [
						["source" : prod.image1],
						["source" : prod.image2],
						["source" : prod.image3],
						["source" : prod.image4],
						["source" : prod.image5],
						["source" : prod.image6]
					]

					bodyContent = [
						"title": "Test - "+prod.title.toString(),
						"category_id": categoryId.toString(),
						"price": prod.price.toFloat(),
						"currency_id": prod.currency.toString(),
						"available_quantity": prod.stock,
						"buying_mode": "buy_it_now",
						"listing_type_id": listing,
						"condition": "new",
						"description": prod.description,
						"pictures": images,
						"warranty": prod.warranty.toString(),
						//"accepts_mercadopago": true,
						//"non_mercado_pago_payment_methods": [["id": "MLAWC"]],
						//"shipping": "not_specified"
					]

					def url = "/items"
					println bodyContent as JSON

					def result = restApiService.post(url , [access_token:token], bodyContent)

//					item.itemId = result.id
//					item.status = result.status
//					item.permalink = result.permalink
//					item.save()
				}
			}
		}
	}

	//	   else {
	//		   if (item.shipping == 'me2') {
	//
	//
	//				   modeShipping = ["mode": "me2",
	//								   "local_pick_up": false
	//								   ]
	//
	//				   }
	//
	//			   else if (item.shipping == 'custom') {
	//
	//				   modeShipping =	["mode": "custom",
	//					   "local_pick_up": false,
	//					   "free_shipping": false,
	//					   "methods": [],
	//					   "costs": [["description": "TEST1","cost": "70"],["description": "TEST2 ","cost": "80"]]
	//					   ]
	//
	//				   }
	//
	//				   else { modeShipping = "not_specified" }
	//
	//
	//				   def images = [
	//						   ["source" : item.product.imagen1],
	//						   ["source" : item.product.imagen2],
	//						   ["source" : item.product.imagen3],
	//						   ["source" : item.product.imagen4],
	//						   ["source" : item.product.imagen5],
	//						   ["source" : item.product.imagen6]
	//					   ]
	//
	//	   bodyContent = [
	//		   "title": "Test - "+item.product.titulo.toString(),
	//		   "category_id": item.categoryId.toString(),
	//		   "price": item.product.precio.toFloat(),
	//		   "currency_id": "ARS",
	//		   "available_quantity": item.product.stock,
	//		   "buying_mode": "buy_it_now",
	//		   "listing_type_id": item.listingType,
	//		   "condition": "new",
	//		   "description": item.product.descripcion,
	//		   "pictures": images,
	//		   "warranty": item.product.garantia.toString(),
	//		   "accepts_mercadopago": true,
	//		   "non_mercado_pago_payment_methods": [["id": "MLAWC"]],
	//		   "shipping": modeShipping
	//
	//
	//	   ]
	//
	//	   }
	//		   println bodyContent as JSON
	//
	//			   def result = RestApiService.post("/items?access_token="+token, bodyContent)
	//			   item.itemId = result.data.id
	//			   item.status = result.data.status
	//			   item.permalink = result.data.permalink
	//			   item.save()
	//		   }
	//	   }
	//
	//	   else {
	//
	//	   if(item.status == "active" || item.status == "paused") {
	//
	//		   def itemAPI = RestApiService.getData("/items/"+item.itemId)
	//
	//		   if (itemAPI.data.sold_quantity == 0) {
	//
	//			   def images = [
	//				   ["source" : item.product.imagen1],
	//				   ["source" : item.product.imagen2],
	//				   ["source" : item.product.imagen3],
	//				   ["source" : item.product.imagen4],
	//				   ["source" : item.product.imagen5],
	//				   ["source" : item.product.imagen6]
	//			   ]
	//
	//			   bodyContent = [
	//				   "title": "Test - "+item.product.titulo.toString(),
	//				   "price": item.product.precio.toFloat(),
	//				   "available_quantity": item.product.stock,
	//				   "pictures": images,
	//				   "warranty": item.product.garantia.toString(),
	//			   ]
	//
	//			   println bodyContent as JSON
	//
	//			   def result = RestApiService.put("/items/"+item.itemId+"?access_token="+token, bodyContent)
	//			   item.permalink = result.data.permalink
	//			   item.save()
	//
	//			   }
	//
	//			   else {
	//				   def images = [
	//					   ["source" : item.product.imagen1],
	//					   ["source" : item.product.imagen2],
	//					   ["source" : item.product.imagen3],
	//					   ["source" : item.product.imagen4],
	//					   ["source" : item.product.imagen5],
	//					   ["source" : item.product.imagen6]
	//				   ]
	//
	//				   bodyContent = [
	//					   "price": item.product.precio.toFloat(),
	//					   "available_quantity": item.product.stock,
	//					   "pictures": images,
	//				   ]
	//
	//				   println bodyContent as JSON
	//
	//				   def result = RestApiService.put("/items/"+item.itemId+"?access_token="+token, bodyContent)
	//				   item.permalink = result.data.permalink
	//				   item.save()
	//
	//
	//			   }
	//		   }
	//	   }
	//
	//   }


	def checkShipping(categoryId) {

		return "not_specified"
	}

	def selectCategory(prod) {

		return "MLA9975"
	}

	def checkListingType(custId, token) {

		def availableListings = []
		def result = restApiService.get("/users/"+custId+"/available_listing_types", [access_token:token])

		if (result) {

			result.available.each()
			{
				availableListings.add(it.id)
			}
		}
		return availableListings
	}

	def updateItems(meliUser) {

		/* get all items by seller*/		
		def parameters = [access_token:meliUser.token]
		def listItems = restApiService.get("/users/"+meliUser.custId+"/items/search", parameters)

		listItems.results.each {

			def meliItem = restApiService.get("/items/"+it, parameters)

			/*check if item is in DB*/
			Items item = Items.findByItemId(it)

			/*if item not exists, create it*/
			if (!item) {

				meliItem.seller_custom_field = "AAA222"
				Products p = Products.findBySku(meliItem.seller_custom_field)
				//Products p = Products.get(1)
				
				if (p) {
				Items i = new Items(categoryId:meliItem.category_id, itemId:meliItem.id, listingType:meliItem.listing_type_id, meliUser:meliUser, permalink:meliItem.permalink, product:p, shipping:meliItem.shipping.mode, status:meliItem.status )
				i.save(flush:true, failOnError:true)
				}

			}
			/*if item exists, update it*/
			else {
				item.status = meliItem.status
				item.shipping = meliItem.shipping.mode
				item.listingType = meliItem.listing_type_id
				item.save()
			}
		}
	}
}
