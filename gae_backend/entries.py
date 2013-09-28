import webapp2
import logging
import datetime
import time
import json
import sys

from google.appengine.ext import db
from datamodel import *

'''
	This will update the tags.MasterId to correspond to
	the correct Master.
'''
class UpdateEntriesHandler(webapp2.RequestHandler):

	def updateTag(self, tagId, masterId, timeStamp):
		try:
			tagKey = db.Key.from_path('Tags', tagId)
			tagDB = db.get(tagKey)
			if(tagDB.timeStamp < timeStamp):

				''' If the masterId in this tag is different than the one that is registering this tag then
					add a timeLog saying this item was added '''
				if tagDB.masterId != masterId:
					masterKey = db.Key.from_path('Masters', masterId)
					masterDB = db.get(masterKey)
					TimeStampLogs(tagId=tagId, tagTimeStamp=timeStamp, tagStatus="Added to", masterName=masterDB.masterName).put()

				tagDB.masterId = masterId
				tagDB.timeStamp = timeStamp
				tagDB.put()

		except:
			logging.error("Unexpected error")
	
	def get(self):
		pass

	def post(self):
		logging.info('Started Updating Entries')

		json_raw = self.request.body
		jsonObj = json.loads(json_raw)

		try: 
			masterId = jsonObj['master_id']
			tagsRegistered = jsonObj['tags']
			tagsRegisteredId = []
			for tag in tagsRegistered:
				tagId = tag['tag_id']
				tagTimestamp = tag['tag_timestamp']
				tagDtstamp = datetime.datetime.strptime(tagTimestamp, '%Y-%m-%d %H:%M:%S')
				self.updateTag(tagId, masterId, tagDtstamp)
				tagsRegisteredId.append(tagId)

			allTagsinMaster = db.GqlQuery("Select * FROM Tags WHERE masterId=:1", masterId)
			for tag in allTagsinMaster:
				if tag.key().name() not in tagsRegisteredId:

					''' If the tag is not in the masters registeredTags list anymore
						then add a timeLog saying tha item was removed from this master'''
					masterKey = db.Key.from_path('Masters', masterId)
					masterDB = db.get(masterKey)
					TimeStampLogs(tagId=tag.key().name(), tagTimeStamp=tag.timeStamp, tagStatus="Removed from", masterName=masterDB.masterName).put()						
					tag.masterId = "Not Registered"
					tag.put()

		except ValueError:
			logging.error('We could not break your Json')

		logging.info('Finished Updating Entries')

'''
	This will give the android app a list of tags 
	registered with that master.
'''
class GetEntriesHandler(webapp2.RequestHandler):

	def get(self):
		allMasters = db.GqlQuery("Select * FROM Masters")

		masters_list = []
		for master in allMasters:
			tagsRegistered = db.GqlQuery("Select * FROM Tags WHERE masterId=:1", master.key().name())
			tags_list = []
			for tag in tagsRegistered:
				tagObj = {}
				tagObj['tag_id'] = tag.key().name()
				tagObj['tag_name'] = tag.tagName
				tags_list.append(tagObj)

			masterObj = {}
			masterObj["master_id"] = master.key().name()
			masterObj["master_name"] = master.masterName
			masterObj["master_tags"] = tags_list
			masters_list.append(masterObj)

		self.response.write(json.dumps(masters_list))

	def post(self):
		pass
