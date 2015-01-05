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
		self._emission_title = soup.title.string
		logging.debug(self._emission_title)

def main():
	ana = EmissionAnalyzer(sys.argv[1])
	ana.parseUrl()


#http://www.francemusique.fr/emission/les-lundis-de-la-contemporaine/2014-2015/carte-blanche-peter-eotvos-l-auditorium-de-la-maison-de-la-radio-01-05-2015-20
if __name__ == "__main__":
    main()