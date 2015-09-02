#!/usr/bin/env python
from bs4 import BeautifulSoup
import urllib2
import logging

from emission import EmissionAnalyzer


def liste():
	logging.debug("Debut liste")
	urlEmissionLundisContemporaine = "http://www.francemusique.fr/emission/les-lundis-de-la-contemporaine/2015-2016"
	
	response = urllib2.urlopen(urlEmissionLundisContemporaine)
	html = response.read()
	soup = BeautifulSoup(html)
	listeUrls = soup.select("ul.list-blck * div.content-teaser > a")
	for u in listeUrls:
		logging.debug(u["href"])
		urlEmission = "http://www.francemusique.fr/"+u["href"]
		ana = EmissionAnalyzer(urlEmission)
		infosEmissions = ana.parseUrl()
		if not infosEmissions.mp3Url is None:
			infosEmissions.download()

def main():
	logging.basicConfig(filename='example.log',level=logging.DEBUG)
	liste()


if __name__ == "__main__":
	main()