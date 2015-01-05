import json
 
json_input = '{"emissionurl":"http://www.francemusique.fr/emission/les-lundis-de-la-contemporaine/2014-2015/l-orchestre-symphonique-de-la-swr-de-baden-baden-et-fribourg-jean-frederic"}'
 
try:
    decoded = json.loads(json_input)
 
    # pretty printing of json-formatted string
    print json.dumps(decoded, sort_keys=True, indent=4)
 
    print "JSON parsing example: ", decoded['emissionurl']
 
except (ValueError, KeyError, TypeError):
    print "JSON format error"