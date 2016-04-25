#!/usr/bin/env python


import web


urls = (
    '/definitionCartographie', 'DefinitionCartographie'
)

app = web.application(urls, globals())

class DefinitionCartographie:        
    def GET(self):
        with open('DefinitionCartographie.xml', 'r') as myfile:
            return myfile.read()


if __name__ == "__main__":
    app.run()
    
    
    
