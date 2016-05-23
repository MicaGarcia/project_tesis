package webserver

class Orders {
	
	String orderId
	String itemId	
	String status
	Integer quantity
	Double totalAmount
	String date_created
	String date_closed
	Buyers buyer	
	String payment
	String shipping
	String buyerFeed
	String sellerFeed

    static constraints = {
    }
}
