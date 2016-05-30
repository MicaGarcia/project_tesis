package webserver

class TestingController {

	def restApiService
	def publisherService
	
    def testingGetMethod() { 
		
		def res = restApiService.get("/sites/MLA")
		println res
	}
	
	def getCategory() {
		
		def prod = Products.get(5)
		def r = publisherService.selectCategory(prod)

	}
}
