package webserver

class Products {

	String sku
	String title
	Double price
	String currency
	String warranty
	String description
	Integer stock
	Long userId //Users user
	static hasMany = [items: Items]
	String image1
	String image2
	String image3
	String image4
	String image5
	String image6

	static constraints = {

		sku blank: false, unique: true
		title blank: false
		price blank: false
		currency blank: false
		warranty blank: false
		description blank: false
		

	}
	static mapping = {
		version false
		  }
}
