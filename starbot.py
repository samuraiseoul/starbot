import os
import time
import re
import string

from slackclient import SlackClient

BOT_ID = os.environ.get("BOT_ID")
READ_WEBSOCKET_DELAY = 1
BOT_TOKEN = os.environ.get('SLACK_BOT_TOKEN')
BOT_DM_STRING = '<@' + BOT_ID + '>'
TEALC_GIF = 'http://4.bp.blogspot.com/-TahRr7ackxY/UU38wPEpacI/AAAAAAAALGA/a8DAVIQYLD0/s1600/indeed.gif'

slack_client = SlackClient(BOT_TOKEN)

def handleMessage(message):
    print message
    if re.search('^' + BOT_DM_STRING, message, re.IGNORECASE):
        print BOT_DM_STRING
        print message.replace(BOT_DM_STRING, '')
        handleDirectMessage(message.replace(BOT_DM_STRING, ''))
    else:
        handleNormalMessage(message)
        
def handleDirectMessage(message):
    print message
    if(re.search('pug bomb', message, re.IGNORECASE)):
        explode = string.split(message.strip(), ' ')
        print explode
        if len(explode) == 2:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text='Finding one pug', as_user=True)
        elif len(explode) == 3:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text='Finding ' + explode[2] + ' pugs', as_user=True)
        else:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text='Invalid Pug Bomb Format pupper!', as_user=True)
                    
    
def handleNormalMessage(message):
    if(re.search('indeed', message, re.IGNORECASE)):
        slack_client.api_call("chat.postMessage", channel=read['channel'], text=TEALC_GIF, as_user=True)
    
if __name__ == '__main__':
    if slack_client.rtm_connect():
        print "StarterBot connected and running!"
        while True:
            readIn = slack_client.rtm_read()
            if readIn and len(readIn):
                for read in readIn:
                    if read['type'] == 'message':
                        if 'user' in read and read['user'] != BOT_ID:
                            handleMessage(read['text'])
            time.sleep(READ_WEBSOCKET_DELAY)
    else:
        print "Something went wrong"