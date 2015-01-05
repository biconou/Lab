from bs4 import BeautifulSoup
import urllib2
import logging

logging.basicConfig(filename='example.log',level=logging.DEBUG)

response = urllib2.urlopen('http://www.francemusique.fr/emission/les-lundis-de-la-contemporaine/2014-2015/l-orchestre-symphonique-de-la-swr-de-baden-baden-et-fribourg-jean-frederic')
html = response.read()
soup = BeautifulSoup(html)


logging.debug(soup.title.string)
