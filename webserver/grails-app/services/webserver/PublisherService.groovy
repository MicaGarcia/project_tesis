package webserver

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class PublisherService {

	def restApiService

	def defineAction(meliUser, prod, listing) {

		if (!prod.items.itemId) {
			publishItem(meliUser, prod, listing)
		}

		else if(prod.items.status.first() == "active" || prod.items.status.first() == "paused") {

			modifyItem(meliUser, prod)
		}
	}

	def publishItem(meliUser, prod, listing) {

		if(prod.stock > 0 && meliUser.token && listing) {

			def bodyContent
			def categoryId
			def modeShipping
			def map = selectCategory(prod)

			if (map) {
				categoryId = map.category
				modeShipping = map.shipMode
			}

			def shipping = createShipping(modeShipping)
			println "SHIPPING FOR ITEM "+shipping

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
				"accepts_mercadopago": true,
				"non_mercado_pago_payment_methods": [["id": "MLAWC"]],
				"shipping": shipping,
				"seller_custom_field":prod.sku
			]

			println bodyContent as JSON

			try {
				def url = "/items"
				def result = restApiService.post(url , [access_token:meliUser.token], bodyContent)
				createItem(meliUser, result)
			}
			catch(e) {
				println "Exception trying to publish an item {$e}"
			}
		}
	}

	def modifyItem(meliUser, prod) {

		try {

			def bodyContent
			def item = restApiService.get("/items/"+prod.items.first().itemId, [access_token:meliUser.token])

			def images = [
				["source" : prod.image1],
				["source" : prod.image2],
				["source" : prod.image3],
				["source" : prod.image4],
				["source" : prod.image5],
				["source" : prod.image6]
			]

			if (item.sold_quantity == 0) {

				bodyContent = [
					"title": prod.title.toString(),
					"price": prod.price.toFloat(),
					"available_quantity": prod.stock,
					"pictures": images,
					"warranty": prod.warranty.toString(),
				]
			}
			else {
				bodyContent = [
					"price": prod.price.toFloat(),
					"available_quantity": prod.stock,
					"pictures": images,
				]
			}

			def parameters = [access_token : meliUser.token]
			def url = "/items/"+item.id
			def result = restApiService.put(url, parameters, bodyContent)
		}
		catch(e) {
			println e
		}
	}

	def createShipping(modeShipping) {

		println "MODE SHIPPING TO CHECK "+modeShipping
		def shipping

		switch (modeShipping) {
			case "me2":
				shipping = ["mode": "me2",
					"local_pick_up": false
				]

				break
			case "custom":
				shipping =	["mode": "custom",
					"local_pick_up": false,
					"free_shipping": false,
					"methods": [],
					"costs": [
						["description": "TEST1","cost": "70"],
						["description": "TEST2 ","cost": "80"]]
				]

				break
			default:
				shipping = "not_specified"
				break
		}

		return shipping
	}

	def selectCategory(prod) {
		def mapCategory
		try {

			def shipping
			def url = "/sites/MLA/category_predictor/predict"
			def parameters = [title: prod.title, price: prod.price]
			def cat = restApiService.get(url, parameters)

			if (cat.shipping_modes.contains("me2")){
				shipping = "me2"
			}
			else {
				shipping = "custom"
			}

			mapCategory = [category: cat.id, shipMode: shipping]
		}
		catch(e){
			println e
		}
		return mapCategory
	}

	def checkListingType(custId, token) {

		def availableListings = []
		def result = restApiService.get("/users/"+custId+"/available_listing_types", [access_token:token])

		if (result) {

			result.available.each() {
				availableListings.add(it.id)
			}
		}
		return availableListings
	}

	def createItem(meliUser, item) {
		try {
			Products p = Products.findBySku(item.seller_custom_field)
			if (p) {
				println "New Item - {$p}"
				Items i = new Items(categoryId: item.category_id, itemId: item.id, listingType: item.listing_type_id, meliUser:meliUser, permalink: item.permalink, product:p, shipping: item.shipping.mode, status: item.status)
				i.save(flush:true, failOnError:true)
			}
		}
		catch(e){
			println "Exception trying to save in DB new item {$e}"
		}
	}

	def updateItems(meliUser) {
		println meliUser.token

		try {
		/* get all items by seller*/		
		def parameters = [access_token:meliUser.token]
		def listItems = restApiService.get("/users/"+meliUser.custId+"/items/search", parameters)
		println listItems
		listItems.results.each {

			def meliItem = restApiService.get("/items/"+it, parameters)
			println meliItem

			/*check if item is in DB*/
			Items item = Items.findByItemId(it)

			/*if item not exists, create it*/
			if (!item) {
				println meliItem
				createItem(meliUser, meliItem)
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
		catch(Exception e) {
			println "Exception trying to update items - {$e}"
		}
	}

	def changeStatus(meliUser, prod) {

		def bodyContent
		try {

			Items item = Items.findByItemId(prod.items.first().itemId)
			println "item change status "+item
			
			if (item) {
				if(item.status == "active") {
					bodyContent = [
						"status": "paused"
					]
				}
				else if(item.status == "paused") {
					bodyContent = [
						"status": "active"
					]
				}

				def parameters = [access_token : meliUser.token]
				def url = "/items/"+item.itemId
				def result = restApiService.put(url, parameters, bodyContent)
				println result

				item.status = result.status
				item.save()
			}
		}
		catch(Exception e) {
			println "error trying to change status item - {$e}"
		}		
	}
	
	def deleteItem(meliUser, prod) {
		
		def bodyContent
		try {

			Items item = Items.findByItemId(prod.items.first().itemId)
			println "item change status "+item
			
			if (item) {

					bodyContent = [
						"deleted":"true"
					]
					
				def parameters = [access_token : meliUser.token]
				def url = "/items/"+item.itemId
				def result = restApiService.put(url, parameters, bodyContent)
				println result

				item.status = result.status
				item.save()
			}
		}
		catch(Exception e) {
			println "error trying to change status item - {$e}"
		}		
	}
	
	def closeItem(meliUser, prod) {
		
		def bodyContent
		try {

			Items item = Items.findByItemId(prod.items.first().itemId)
			println "item change status "+item
			
			if (item.status == "active" || item.status == "paused") {
				
				bodyContent = [					
					"status": "closed"
					]
					
				def parameters = [access_token : meliUser.token]
				def url = "/items/"+item.itemId
				def result = restApiService.put(url, parameters, bodyContent)
				println result

				item.status = result.status
				item.save()
			}
		}
		catch(Exception e) {
			println "error trying to change status item - {$e}"
		}
	}
	
	def relist(meliUser, prod) {
		
		def bodyContent
		try {

			Items item = Items.findByItemId(prod.items.first().itemId)
			println "item change status "+item
			
			if (item.status == "closed" && item.product.stock > 0 ) {
				
				bodyContent = [
					 "price": prod.price.toFloat(),
					 "quantity": prod.stock,
					 "listing_type_id": item.listingType
					]
					
				def parameters = [access_token : meliUser.token]
				def url = "/items/"+item.itemId+"/relist"
				def result = restApiService.post(url, parameters, bodyContent)
				println result

				item.itemId = result.id
				item.status = result.status
				item.permalink = result.permalink
				item.save()
			}
		}
		catch(Exception e) {
			println "error trying to change status item - {$e}"
		}
		
	}
}
