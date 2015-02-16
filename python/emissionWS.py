#!/usr/bin/env python
import web
from bs4 import BeautifulSoup
import urllib2
import logging
import json


logging.basicConfig(filename='example.log',level=logging.DEBUG)


urls = (
    '/emission', 'emission_analyzer'
)

app = web.application(urls, globals())

class emission_analyzer:        
    def POST(self):
		output = '{"titre":"'
		#i = web.input()
		json_input = web.data()
		decoded = json.loads(json_input)
		emissionUrl = decoded['emissionurl']
		#logging.debug(i.emissionurl)
		logging.debug(emissionUrl)
		#response = urllib2.urlopen('http://www.francemusique.fr/emission/les-lundis-de-la-contemporaine/2014-2015/l-orchestre-symphonique-de-la-swr-de-baden-baden-et-fribourg-jean-frederic')
		response = urllib2.urlopen(emissionUrl)
		html = response.read()
		soup = BeautifulSoup(html)
		logging.debug(soup.title.string)
		output += soup.title.string
		output += '"}'
		return output


if __name__ == "__main__":
    app.run()