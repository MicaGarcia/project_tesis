package webserver

import grails.transaction.Transactional

@Transactional
class UsersService {

	def restApiService

	def getMeliUser(sessionId) {
		def meliUser
		def error

		try {
			Users u = Users.get(sessionId)
			meliUser = MeliUsers.findByUser(u)
		}
		catch (e) {
			error = "There was an error searching Meli User - {e}"
		}
		return meliUser
	}

	def getDataLogged(token) {
		def error
		def result

		try {
			def parameters = [access_token:token]			
			result = restApiService.get("/users/me", parameters)
		}
		catch (e) {
			error = "There was an error getting data logged - {$e}"
			println "There was an error getting data logged - {$e}"
		}
		return result
	}

	def createMeliUser(userId, sessionToken, sessionRefresh, sessionMeliId) {
		def result = true
		def error
		Users u = Users.get(userId)

		try {
			MeliUsers mu = new MeliUsers(custId: sessionMeliId, refreshToken: sessionRefresh, token: sessionToken, user: u).save(flush: true)
			println mu
			if (!mu.save()) {
				mu.errors.allErrors.each { println it }
				result = false
			}
		}
		catch(Exception e) {
			println "There was an error saving meli user - {$e}"
			error = "There was an error saving meli user - {$e}"
			result = false
		}
		return result
	}

	def updateMeliUser(userId, sessionToken, sessionRefresh, sessionMeliId) {
		def error

		Users u = Users.get(userId)
		try {

			MeliUsers mu = MeliUsers.findByUser(u)			
			mu.custId = sessionMeliId
			mu.refreshToken = sessionRefresh
			mu.token = sessionToken
			mu.save()
			
			if (!mu.save()) {
				mu.errors.allErrors.each { println it }
				result = false
			}
			
		}
		catch(Exception e) {
			error = "There was an error updating meli user - {$e}"
			println "There was an error updating meli user - {$e}"
		}
	}
	
	def upRefresh(meliUser_id, token, refresh) {
		def error 
		try {
		MeliUsers mu = MeliUsers.get(meliUser_id)
		
		mu.token = token
		mu.refreshToken = refresh
		mu.save()
		}
		catch(Exception e) {
			error = "There was an error refreshing token - {e}"
		}
			
	}
}
