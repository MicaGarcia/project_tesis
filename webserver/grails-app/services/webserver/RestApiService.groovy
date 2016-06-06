package webserver

import grails.transaction.Transactional
import grails.util.Holders
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.JSON
import groovyx.net.http.Method

@Transactional
class RestApiService {

	// Handle the global variables (config.groovy)
	def grailsApp = Holders.getGrailsApplication()

	def get(url, parameters) {

		def http = new HTTPBuilder(grailsApp.config.grails.apiURL)
		def json

		try {
			json = http.get( path : url, query : parameters)

			//asserts!!
		}
		catch (e) {
			println "Exception GET "+e.message()
		}
		return json

	}

	def post(url, parameters, body) {

		def http = new HTTPBuilder(grailsApp.config.grails.apiURL)
		def jsonResponse

		try {
			http.handler.failure = { resp ->
				println "Unexpected failure: ${resp.status}"
				println "Unexpected failure: ${resp}"
			}

			http.post( path: url, query: parameters,  body: body,
			requestContentType: JSON ){ resp, json ->
				println "RESP ${resp}"
				//assert resp.statusLine.statusCode == 200
				jsonResponse = json
			}
		}
		catch (Exception e){
			println "POST Exception"+ e
		}
		return jsonResponse
	}

	def put(url, parameters, body) {

		def method = Method.PUT
		def http = new HTTPBuilder(grailsApp.config.grails.apiURL)
		def jsonResponse
		try {
			http.handler.failure = { resp ->
				println "Unexpected failure: ${resp.status}"
				println "Unexpected failure: ${resp}"
			}

			http.request(method, path: url, query: parameters,  body: body,
			requestContentType: JSON ){ resp, json ->
				println "RESP ${resp}"
				//assert resp.statusLine.statusCode == 200
				jsonResponse = json
			}
		}
		catch (Exception e){
			println "PUT Exception"+ e
		}

		return jsonResponse
	}

	def delete(url, body) {

		def http = new HTTPBuilder(grailsApp.config.grails.apiURL)

		def jsonResponse

		http.put( path: url, body: body,
		requestContentType: URLENC ) { resp, json ->

			assert resp.statusLine.statusCode == 200

			jsonResponse = json

		}
		println "JSON RESPONSE" + jsonResponse
		return jsonResponse
	}

}
