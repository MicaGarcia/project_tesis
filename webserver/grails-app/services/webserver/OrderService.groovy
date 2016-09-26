package webserver

import grails.transaction.Transactional

@Transactional
class OrderService {

	def restApiService

	def updateOrders(meliUser) {

		def listOrders
		try {
		/* get all orders by seller*/
		def parameters = [seller: meliUser.custId, access_token:meliUser.token]
		listOrders = restApiService.get("/orders/search", parameters)

		listOrders.results.each { 
			println it.id
			findOrder(it) 
			}
		}
		catch (Exception e){
			println "Error trying to update orders list - ${e}"
		}
		
		def res = getOrders(meliUser)
		return res
	}

	def getOrders(meliUser) {
		def listOrders = []

		try {
			def listAllOrders = Orders.getAll()

			listAllOrders.each() {
				

				Items item = Items.findByItemId(it.itemId)
				if (item!= null && item.meliUser == meliUser) {
					listOrders.add(it)
				}
			}
		}
		catch(Exception e) {
			println "ERROR - Trying to get orders - ${e}"
		}
		return listOrders
	}

	def findOrder(meliOrder) {
		
		try {

		Orders order = Orders.findByOrderId(meliOrder.id)
		if (!order) {
			
			def shippingOrder = "no shipping"
			def paymentOrder = "no payment"

			if(meliOrder.shipping.status) {
				shippingOrder = meliOrder.shipping.status
			}
			if(meliOrder.payments.status) {
				paymentOrder = meliOrder.payments.status.toString()
			}

			
			println "PAYMENT "+meliOrder.payment
			println "PAYMENT "+paymentOrder

			Orders o = new Orders(itemId: meliOrder.order_items.item.id[0], itemTitle: meliOrder.order_items.item.title[0], orderId: meliOrder.id, status: meliOrder.status, quantity: meliOrder.order_items.quantity[0], totalAmount: meliOrder.total_amount, date_created: meliOrder.date_created, date_closed: meliOrder.date_closed, /*buyer_id: meliOrder.buyer.id,*/ shipping: shippingOrder, payment: paymentOrder,  buyerFeed: meliOrder.feedback.purchase.toString(), sellerFeed: meliOrder.feedback.sale.toString())
			o.save(flush:true, failOnError:true)

			/*update stock*/
			Items item = Items.findByItemId(o.itemId)
			def stock = item.product.stock - o.quantity
			item.product.stock = stock
			item.save()
			
			
			if (!item.save()) {
				item.errors.allErrors.each { println it }
				result = false
			}
		}

		else {
			if(meliOrder.payments.status) {
				order.payment = meliOrder.payments.status.toString()
			}

			if (meliOrder.shipping.status) {
				order.shipping = meliOrder.shipping.status.toString()
			}

			order.status = meliOrder.status
			order.date_closed = meliOrder.date_closed


			if (meliOrder.feedback.purchase?.fulfilled) {
				order.buyerFeed = meliOrder.feedback.purchase.fulfilled.toString()
			}
			else {
				order.buyerFeed = "null"
			}


			if (meliOrder.feedback.sale?.fulfilled) {
				order.sellerFeed = meliOrder.feedback.sale.fulfilled.toString()
			}
			else {
				order.sellerFeed = "null"
			}
			order.save()
		}
		}
		catch(Exception e) {
			println "Error trying to save order - ${e}"
		}
	}
}
