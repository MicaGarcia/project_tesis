package webserver

import grails.transaction.Transactional
import grails.util.Holders
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.URLENC

@Transactional
class RestApiService {

	// Handle the global variables (config.groovy)
	def grailsApp = Holders.getGrailsApplication()

	def get(url, parameters) {

		def http = new HTTPBuilder(grailsApp.config.grails.apiURL)
		def json
		println url

		try {
			json = http.get( path : url, query : parameters)

			//asserts!!
			println "JSON GET "+json
		}
		catch (e) {
			println "Exception GET "+e.message()

		}
		return json

	}

	def post(url, parameters, body) {
		println url

		def http = new HTTPBuilder(grailsApp.config.grails.apiURL)
		def jsonResponse
		
		try {
			
			http.post( path: url, query : parameters, body: body,
			requestContentType: URLENC ) 
			
			{ resp, json ->

				//assert resp.statusLine.statusCode == 200
				jsonResponse = json
			}
		}
		catch (Exception e){
			println e
		}
		println "JSON RESPONSE"+jsonResponse
		return jsonResponse
	}

	def put(url, parameters, body) {

		def http = new HTTPBuilder(grailsApp.config.grails.apiURL)

		def jsonResponse

		http.put( path: url, body: body,
		requestContentType: URLENC ) { resp, json ->

			assert resp.statusLine.statusCode == 200

			jsonResponse = json

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
