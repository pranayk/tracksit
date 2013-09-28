import webapp2
import logging
import datetime
import time
import json
import sys

from google.appengine.ext import db
from datamodel import *

'''
	This class is used to update the db.
	The android will be using this.
'''
class UpdateComponentsHandler(webapp2.RequestHandler):
	
	@db.transactional
	def updateMaster(self, masterId, masterName):
		try:
			masterKey = db.Key.from_path('Masters', masterId)
			masterDB = db.get(masterKey)
			masterDB.masterName = masterName
			masterDB.put()
		except BadKeyError:
			logging.error("Could not find the key")

	@db.transactional
	def updateTag(self, tagId, tagName):
		try:
			tagKey = db.Key.from_path('Tags', tagId)
			tagDB = db.get(tagKey)
			tagDB.tagName = tagName
			tagDB.put()
		except BadKeyError:
			logging.error("Could not find the key")

	def get(self):
		logging.info("It souldnt be here")

	def post(self):
		logging.info('Started Updating')

		json_raw = self.request.body
		jsonObj = json.loads(json_raw)

		try: 
			mastersUpdate = jsonObj['masters']

			for master in mastersUpdate:
				masterId = master['master_id']
				masterName = master['master_name']
				logging.info(masterId + masterName)
				self.updateMaster(masterId, masterName)


			tagsUpdate = jsonObj['tags']

			for tag in tagsUpdate:
				tagId = tag['tag_id']
				tagName = tag['tag_name']
				self.updateTag(tagId, tagName)

		except ValueError:
			logging.error("We could not break your Json")

		logging.info('Finished Updating')

'''
	This gets all the components currently in the db.
'''
class GetAllComponentsHandler(webapp2.RequestHandler):

	def get(self):
		allMasters = {}
		allMasters = db.GqlQuery("Select * FROM Masters")

		masters_list = []
		for master in allMasters:
			masterObj = {}
			masterObj["master_id"] = master.key().name()
			masterObj["master_name"] = master.masterName
			masters_list.append(masterObj)

		allTags = {}
		allTags = db.GqlQuery("Select * FROM Tags")
		tags_list = []
		for tag in allTags:
			tagObj = {}
			tagObj["tag_id"] = tag.key().name()
			tagObj["tag_name"] = tag.tagName
			tags_list.append(tagObj)

		self.response.write(json.dumps({"masters" : masters_list, "tags" : tags_list}))
		


	def post(self):
		logging.info("It souldnt be here")
