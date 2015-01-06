#!/usr/bin/env python
from bs4 import BeautifulSoup
import urllib2
import logging
import json
import sys


logging.basicConfig(filename='example.log',level=logging.DEBUG)


class EmissionAnalyzer: 
	def __init__(self, url):
		self._url = url
	def parseUrl(self):
		logging.debug(self._url)
		response = urllib2.urlopen(self._url)
		html = response.read()
		soup = BeautifulSoup(html)
		self._emission_title = soup.find("meta",property="og:title")['content']
		self._emission_image_url = soup.find("meta",property="og:image")['content']
		logging.debug(self._emission_title)
		logging.debug(self._emission_image_url)
		logging.debug(json.dumps(self))
		

def main():
	if len(sys.argv) > 1:
		urlToAnalyze = sys.argv[1]
	else:
		logging.debug("Utilisation de l'URL de test")
		urlToAnalyze = "http://www.francemusique.fr/emission/les-lundis-de-la-contemporaine/2014-2015/carte-blanche-peter-eotvos-l-auditorium-de-la-maison-de-la-radio-01-05-2015-20"
	ana = EmissionAnalyzer(urlToAnalyze)
	ana.parseUrl()


#http://www.francemusique.fr/emission/les-lundis-de-la-contemporaine/2014-2015/carte-blanche-peter-eotvos-l-auditorium-de-la-maison-de-la-radio-01-05-2015-20
if __name__ == "__main__":
    main()