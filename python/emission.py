#!/usr/bin/env python
from bs4 import BeautifulSoup
import urllib2
import logging
import sys
import os
import unicodedata
import re
import json
import io
import html2text





class EmissionInfos:
	emissionUrl = ""
	title = ""
	imageUrl = ""
	mp3Url = ""
	mp3FileName = ""
	date = ""
	
	def download(self):
		
		dirName = "%s - %s" % (self.date, self.title)
		dirName = unicodedata.normalize('NFKD', dirName).encode('ascii', 'ignore')
		dirName = re.sub('[^\w\s-]', '', dirName).strip().lower()	
				
		if not os.path.exists(dirName):
			logging.debug("Create directory " + dirName)
			os.makedirs(dirName)
		
		#with io.open(dirName + "/donnees.json", 'w', encoding='utf8') as json_file:
		#	json.dump(self.__dict__, json_file, ensure_ascii=False)
			
		fichierDonnees = io.open(dirName + "/donnees.json", mode="w", encoding="utf8")
		#fichierDonnees = open(dirName + "/donnees.json","w")
		fichierDonnees.write(json.dumps(self.__dict__, ensure_ascii=False))
		fichierDonnees.close
		
		#logging.debug("Creation du fichier texte ")	
		#htmlEmission = urllib2.urlopen(self.emissionUrl).read()
		#output = open(dirName + "/emission.txt",'wb')
		#output.write(html2text.html2text(htmlEmission.decode('utf8')))
		#output.close()
		
		#logging.debug("Telechargement de l'image " + self.imageUrl)	
		imageFile = urllib2.urlopen(self.imageUrl)
		output = open(dirName + "/Folder.png",'wb')
		output.write(imageFile.read())
		output.close()

		logging.debug("Telechargement du mp3 " + self.mp3Url)	
		mp3Stream = urllib2.urlopen(self.mp3Url)
		output = open(dirName + "/" + self.mp3FileName,'wb')
		output.write(mp3Stream.read())
		output.close()


class EmissionAnalyzer: 
	def __init__(self, url):
		self._url = url
	def parseUrl(self):
		infos = EmissionInfos()
		logging.debug(self._url)
		infos.emissionUrl=self._url
		response = urllib2.urlopen(self._url)
		html = response.read()
		soup = BeautifulSoup(html)
		# ------------------
		# Recherche du titre
		# ------------------
		titleTag = soup.select("div.article > h1")[0]
		infos.title = titleTag.contents[0]
		logging.debug(infos.title)
		# ---------------------
		# Recherche de la date
		# ---------------------
		infos.date = soup.find("span",class_="date-display-single")['content']
		infos.date = re.findall("[0-9]{4}\-[0-9]{2}\-[0-9]{2}", infos.date)[0]
		infos.date = re.sub('\-', '', infos.date)
		logging.debug(infos.date)
		# ------------------------------
		# Recherche de l'URL de l'image
		# ------------------------------
		#infos.imageUrl = soup.find("meta",property="og:image")['content']	
		imageTag = soup.select("div.article > div.photo * img")[0]
		infos.imageUrl = imageTag['src']
		logging.debug(infos.imageUrl)
		# ---------------------------
		# Recherche de l'URL du mp3
		# ---------------------------
		try:
			playerUrl = soup.find("a",class_="jp-play")['href']
			playerCompleteUrl = "http://www.francemusique.fr" + playerUrl
			playerResponse = urllib2.urlopen(playerCompleteUrl)
			playerHtml = playerResponse.read()
			playerSoup = BeautifulSoup(playerHtml)
			infos.mp3Url = playerSoup.find("a",id="player")['href']
			infos.mp3FileName = re.split("/",infos.mp3Url)[-1]
		except:
			infos.mp3Url = None
		logging.debug(infos.mp3Url)
		logging.debug(infos.mp3FileName)
		return infos

def main():
	if len(sys.argv) > 1:
		urlToAnalyze = sys.argv[1]
	else:
		urlToAnalyze = raw_input("URL de l'emission : ")
	os.remove('example.log')
	logging.basicConfig(filename='example.log',level=logging.DEBUG)
	ana = EmissionAnalyzer(urlToAnalyze)
	infosEmissions = ana.parseUrl()	
	infosEmissions.download()


if __name__ == "__main__":
	main()