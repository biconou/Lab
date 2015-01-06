#!/usr/bin/env python
from bs4 import BeautifulSoup
import urllib2
import logging
import json
import sys


logging.basicConfig(filename='example.log',level=logging.DEBUG)

class EmissionInfos:
	title = ""
	imageUrl = ""


class EmissionAnalyzer: 
	def __init__(self, url):
		self._url = url
	def parseUrl(self):
		infos = EmissionInfos()
		logging.debug(self._url)
		response = urllib2.urlopen(self._url)
		html = response.read()
		soup = BeautifulSoup(html)
		infos.title = soup.find("meta",property="og:title")['content']
		infos.imageUrl = soup.find("meta",property="og:image")['content']
		logging.debug(infos.title)
		logging.debug(infos.imageUrl)
		# Telechargement de l'image
		imageFile = urllib2.urlopen(infos.imageUrl)
		output = open("Folder.png",'wb')
		output.write(imageFile.read())
		output.close()
		# Telechargement du fichier mp3
		playerUrl = soup.find("a",class_="jp-play")['href']
		playerCompleteUrl = "http://www.francemusique.fr" + playerUrl
		logging.debug(playerCompleteUrl)
		playerResponse = urllib2.urlopen(playerCompleteUrl)
		playerHtml = playerResponse.read()
		playerSoup = BeautifulSoup(playerHtml)
		mp3Url = playerSoup.find("a",id="player")['href']
		logging.debug(mp3Url)
		mp3File = urllib2.urlopen(mp3Url)
		output = open("emission.mp3",'wb')
		output.write(mp3File.read())
		output.close()
		#logging.debug(json.dumps(infos))
		

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