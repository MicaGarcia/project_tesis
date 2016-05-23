package webserver

import static groovyx.net.http.ContentType.URLENC
import groovyx.net.http.HTTPBuilder
import grails.util.Holders

class HomeController {

	// Handle the global variables (config.groovy)
	def grailsApp = Holders.getGrailsApplication()

	def usersService
	def restApiService

	def clientId =	"1094089876635327"
	def secretId =  "hR0yqAR3CmJZIiXcNfNNkHRiLX94rqPa"
	def redirectUri = "http://localhost:8080/webserver/home/authenticate"

	def index() {

		try {
			if(!session.user) {
				redirect(action:"login")
			}

			else {
				redirect(action:"configuration")
			}
		}
		catch (e) {
			redirect(action:"login")
		}
	}

	def login() {}

	def userLogin() {

		def username = params.username
		def password = params.password

		try {
			def user = Users.findByUsernameAndPassword(username, password)
			println user

			if (user) {
				session.user = user
				session.setMaxInactiveInterval(3600)
				session.meliUser = usersService.getMeliUser(session.user.id)

				//				if(session.meliUser) {
				//
				//					refreshToken(session.meliUser)
				//				}
				redirect (action:"configuration")
			}

			else {
				flash.message = "Sorry, ${params.username}. Please try again."
				redirect(action:"login")
			}
		}
		catch (e) {
			println e
			flash.message = "Sorry, ${params.username}. Please try again."
			redirect(action:"login")
		}
	}

	def logout() {
		if(session.user) {
			session.invalidate()
		}
		redirect(action:"login")
	}

	def configuration() {

		try {

			println "SESSION MELIUSER "+session.meliUser
			println "SESSION USER "+session.user

			def userLogged
			//def meliUser = usersService.getMeliUser(session.user.id)

			if (!session.meliUser) {

				def sessionToken = session.token
				def sessionRefresh = session.refreshToken
				def sessionMeliId = session.meliCustId

				if (sessionToken) {

					try {
						userLogged = usersService.getDataLogged(sessionToken)
						def listShipping = []
						listShipping = userLogged.shipping_modes

						def result = usersService.createMeliUser(session.user.id, sessionToken, sessionRefresh, sessionMeliId)
						if (result) {
							render(view: "configuration", model: [userLogged: userLogged, listShipping:listShipping])
						}
						else {
							//render(view: "configuration") -- returns error --
						}
					}
					catch(Exception e) {
						//render(view: "configuration") -- returns error --
					}
				}
			}
			else {

				def token = session.meliUser.token
				def refreshToken = session.meliUser.refreshToken
				def meliCustId = session.meliUser.custId

				//usersService.updateMeliUser(session.user.id, sessionToken, sessionRefresh, sessionMeliId)

				userLogged = usersService.getDataLogged(token)
				def listShipping = []
				listShipping = userLogged.shipping_modes
				render(view: "configuration", model: [userLogged: userLogged, listShipping:listShipping])

			}
		}
		catch (e) {
			println e
			redirect (action:"login")
		}
	}

	def authenticate() {
		def error
		def codeMeli = params.code
		println codeMeli

		try {

			def postBody = [grant_type: 'authorization_code',
				client_id: clientId,
				client_secret: secretId,
				code: codeMeli,
				redirect_uri: redirectUri]

			def r = restApiService.post('/oauth/token', null, postBody)

			session.token = r.access_token
			session.refreshToken = r.refresh_token
			session.meliCustId = r.user_id

			redirect(action: "configuration")
		}

		catch (Exception e) {
			println e
			error = "There was an error in authentication - {e}"
		}
	}

	def oauth() {
		redirect(url: "https://auth.mercadolibre.com.ar/authorization?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUri)
	}

	//	def refreshToken(meliUser) {
	//
	//		def url = "/oauth/token?grant_type=refresh_token&client_id="+clientId+"&client_secret="+secretId+"&refresh_token="+meliUser.refreshToken
	//		def result = restApiService.post(url, null)
	//		println "result "+ result
	//		try {
	//			usersService.upRefresh(meliUser.id, result.access_token, result.refresh_token)
	//
	//		}
	//
	//		catch(Exception e) {
	//
	//			flash.message = "Sorry, System is not available"
	//
	//		}
	//
	//	}
}
