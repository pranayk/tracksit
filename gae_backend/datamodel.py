import datetime
from google.appengine.ext import db
from google.appengine.api import users

"""
Database models used in the Trackit application.

MASTERS : Stores the Masters information that record the RFID tags
	MASTERID
	MASTERNAME

TAGS : Stores the tags information that are cuurently used
	TAGSID
	TAGSNAME
	TAGSID
	MASTERID
	TIMESTAMP
"""


class Masters(db.Model):
	masterName = db.StringProperty(required=True)
  
class Tags(db.Model):
	tagName = db.StringProperty(required=True)
	timeStamp = db.DateTimeProperty(required=True)
	masterId = db.StringProperty(required=True)

class TimeStampLogs(db.Model):
	tagId = db.StringProperty(required=True)
	tagTimeStamp = db.DateTimeProperty(required=True)
	tagStatus = db.StringProperty(required=True)
	masterName = db.StringProperty(required=True)
	
