package webserver

import grails.transaction.Transactional

@Transactional
class NotificationService {

	def questionService
	def orderService

	def saveNotification(JSONObject) {

		try {
			Notifications not = new Notifications(userId: JSONObject.user_id, resource: JSONObject.resource, topic: JSONObject.topic, date_received: JSONObject.received, date_sent: JSONObject.sent, status: "not processed")
			not.save(flush:true, failOnError:true)
		}
		catch(Exception e) {
			println "The notification wasn't saved - ${e}"
		}
	}

	def processNotifications(user) {

		def listNotifications = []
		try {
			
			MeliUsers meliUser = MeliUsers.findByUser(user)
			listNotifications = Notifications.findAllByStatusAndUserId("not processed", meliUser.custId)
		}
		catch(e) {
			println "Notifications couldn't been processed - ${e}"
		}

		listNotifications.each() {

			try {

				def notification = it
				def parameters = [access_token: meliUser.token]
				def result = restApiService.get(notification.resource, parameters)

				switch (notification.topic) {

					case "items":
						Items item = Items.findByItemId(result.id)
						item.status = result.status
						item.shipping = result.shipping.mode
						item.listingType = result.listing_type_id
						item.save()

						break

					case "questions" :
						questionService.findQuestion(result)

						break

					case "orders" :
						orderService.findOrder(result)
						break
				}
				notification.status = "processed"
			}

			catch(e) {
				println "Notification ${it} - wasn't processed - ${e.message()}"
			}
		}
	}
}
