import webapp2
import logging
import datetime
import time

from datamodel import *

class TestGenerateHandler(webapp2.RequestHandler):
	
	def get(self):
		
		logging.info('Started TestGenerateHandler')

		''' Hacking the tags and masters generate,  UOFTHACKS - we do too'''
		dtstamp = datetime.datetime.fromtimestamp(time.time())

		Tags(key_name="ABCD", tagName="Y", timeStamp=dtstamp, masterId="NR").put()
		Tags(key_name="DCEF", tagName="O", timeStamp=dtstamp, masterId="NR").put()
		Tags(key_name="GHIF", tagName="U", timeStamp=dtstamp, masterId="NR").put()
		Tags(key_name="HELL", tagName="SU", timeStamp=dtstamp, masterId="NR").put()
		Tags(key_name="OOOO", tagName="CK", timeStamp=dtstamp, masterId="NR").put()

		Masters(key_name="M1", masterName="Master 1").put()
		Masters(key_name="M2", masterName="Master 2").put()

		logging.info('Initial data added. Phew!')

	def post(self):
		pass