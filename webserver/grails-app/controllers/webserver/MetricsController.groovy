package webserver

class MetricsController {
	
	def usersService
	def restApiService

    def index() {
		
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		def userInfo = usersService.getDataLogged(meliUser.token)
		
		def paramsTotal = [access_token:meliUser.token]
		def totalItems = restApiService.get("/users/"+meliUser.custId+"/items/search", paramsTotal)
		
		def paramsActive = [access_token:meliUser.token, status:"active"]
		def activeItems = restApiService.get("/users/"+meliUser.custId+"/items/search", paramsActive)
		
		def paramsPaused = [access_token:meliUser.token, status:"paused"]
		def pausedItems = restApiService.get("/users/"+meliUser.custId+"/items/search", paramsPaused)
		
		def paramsClosed = [access_token:meliUser.token, status:"closed"]
		def closedItems = restApiService.get("/users/"+meliUser.custId+"/items/search", paramsClosed)
		
		def listHotProds = getHotProds()
		
		render(view: "index", model: [userInfo: userInfo, totalItems:totalItems, active: activeItems, paused: pausedItems, closed: closedItems, quantityVisits: listHotProds.total_visits ])
	
	}
	
	def getHotProds() {
		
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		def paramsVisits = [access_token:meliUser.token, date_from:"2016-06-01T00:00:00.000-00:00", date_to:"2016-06-10T00:00:00.000-00:00"]
		
		def visits = restApiService.get("/users/"+meliUser.custId+"/items_visits", paramsVisits)
		
		return visits
				
	}
}
