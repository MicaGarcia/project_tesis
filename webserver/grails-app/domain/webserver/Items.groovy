package webserver

class Items {

	String itemId
	String status
	String categoryId
	String shipping
	String listingType
	MeliUsers meliUser
	Products product
	String permalink
	
	static constraints = {
		
		itemId unique: true //, nullable: true
//		status nullable: true
//		categoryId nullable: true
//		shipping nullable: true
//		listingType nullable: true
//		meliUser nullable : true
//		permalink nullable : true
		
	}
}
