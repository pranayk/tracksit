import webapp2
import logging
import datetime
import time
import json
import sys

from google.appengine.ext import db
from datamodel import *

'''
	This class implments the handler for getting
	the latest fifteen timelogs of that Tag 
'''
class GetTimeLogHandler(webapp2.RequestHandler):

	def get(self):
		logging.info("It souldnt be here")

	def post(self):
		logging.info('Started Crunching Time Logs')

		json_raw = self.request.body
		jsonObj = json.loads(json_raw)

		try:
			tagId = jsonObj['tag_id']
			
		except ValueError:
			logging.error("We could not break your Json")

		logging.info('Finished Crunching Time Logs')