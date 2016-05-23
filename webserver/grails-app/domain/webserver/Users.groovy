package webserver

class Users {

	Integer id
	String username
	String password
	String email

	static hasOne = [meliUser : MeliUsers]

	static constraints = {}
	
	static mapping = { version false }
}
