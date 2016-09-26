package webserver

import grails.transaction.Transactional

@Transactional
class FeedbackService {

	def restApiService

	def giveFeedback(meliUser, params) {
		
		println params

		def orderId = params.orderId
		def rating = params.feedback
		def reason = params.reason
		def message = params.feedText
		def token = meliUser.token
		def bodyContent

		try {

			Orders order = Orders.get(orderId)

			if (order) {

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
			}

			def parameters = [access_token: meliUser.token]
			def result = restApiService.post("/orders/"+order.orderId+"/feedback", parameters, bodyContent)
			order.sellerFeed = result.fulfilled
			order.save()
		}
		catch(Exception e) {
			println "Error trying to give feedback - ${e}"
		}
	}
}
