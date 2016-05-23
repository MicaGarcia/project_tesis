package webserver

import grails.transaction.Transactional

@Transactional
class QuestionService {

	def restApiService

	def updateQuestions(meliUser) {
		/* get all questions by seller*/
		def parameters = [seller_id:meliUser.custId, access_token:meliUser.token]
		def listQuestions = restApiService.get("/questions/search", parameters)

		listQuestions.questions.each { findQuestion(it) }
	}

	def getQuestions(meliUser) {
		def listQuestions = []

		try {
			def listAllQuestions = Questions.getAll()
			listAllQuestions.each() {
				if (it!= null && it.item.meliUser == meliUser) {
					listQuestions.add(it)
				}
			}
		}
		catch(Exception e) {
			println "ERROR - Trying to get questions - ${e}"
		}

		return listQuestions
	}

	def answerQuestion(meliUser, questionId, answerText) {
		
		def result = false
		try {
			Questions q = Questions.get(questionId)

			def bodyContent = [
				"question_id": q.questionId,
				"text": answerText.toString()
			]

			println meliUser.token
			def parameters = [access_token: meliUser.token]
			def r = restApiService.post("/answers", parameters, bodyContent)
			q.status = r.status
			q.save(flush:true,failOnError:true)
			result true
		}
		catch(e) {

		}

		return result
	}

	def findQuestion(question) {

		Questions ques = Questions.findByQuestionId(question.id)

		/*if question not exists, create in DB*/
		if (!ques) {

			Items item = Items.findByItemId(question.item_id)
			Questions q = new Questions(questionId: question.id, item: item, status: question.status, date: question.date_created, text: question.text)
			q.save(flush:true, failOnError:true)
		}
		/*if exists, update it*/
		else {
			ques.status = question.status
			ques.save()
		}
	}
}
