package webserver

class TestingController {

	def restApiService
	
    def testingGetMethod() { 
		
		def res = restApiService.get("/sites/MLA")
		println res
	}
}
