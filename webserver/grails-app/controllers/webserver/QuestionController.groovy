package webserver

class QuestionController {

	def questionService

	def index() {
		if(!session.user) {
			redirect (controller: "home", action:"login")
			return false
		}
		else {
			redirect(controller:"question", action : "list")
		}
	}

	def list() {

		if(!session.user) {
			redirect (controller: "home", action:"login")
			return false
		}
			
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		def listQuestions = questionService.getQuestions(meliUser)

		render (view:"list", model: [listQuestions:listQuestions])
	}

	def answerQuestion() {

		try {
			MeliUsers meliUser = MeliUsers.findByUser(session.user)
		
		def res = questionService.answerQuestion(meliUser, params.questionId, params.answer)
		}
		catch(e) {}
		redirect(action: "list")
	}
	
	def updateList() {	
		MeliUsers meliUser = MeliUsers.findByUser(session.user)
		def res = questionService.updateQuestions(meliUser)

		def listQuestions = questionService.getQuestions(meliUser)
		render (view:"list", model: [listQuestions:listQuestions])
	}
}

