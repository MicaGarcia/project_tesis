package webserver

class MeliUsers {


	Long custId
	String token
	String refreshToken
	Users user

	static constraints = {}
	
	static mapping = { version false }
}
