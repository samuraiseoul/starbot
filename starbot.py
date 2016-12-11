import os
import time
import re

from slackclient import SlackClient

BOT_ID = os.environ.get("BOT_ID")
READ_WEBSOCKET_DELAY = 1
BOT_TOKEN = os.environ.get('SLACK_BOT_TOKEN')
TEALC_GIF = 'http://4.bp.blogspot.com/-TahRr7ackxY/UU38wPEpacI/AAAAAAAALGA/a8DAVIQYLD0/s1600/indeed.gif'

slack_client = SlackClient(BOT_TOKEN)

if __name__ == '__main__':
    if slack_client.rtm_connect():
        print "StarterBot connected and running!"
        while True:
            readIn = slack_client.rtm_read()
            if readIn and len(readIn):
                for read in readIn:
                    if read['type'] == 'message':
                        if 'user' in read and read['user'] != BOT_ID:
                            text = read['text']
                            if re.search('indeed', text, re.IGNORECASE):
                                slack_client.api_call("chat.postMessage", channel=read['channel'], text=TEALC_GIF, as_user=True)
            time.sleep(READ_WEBSOCKET_DELAY)
    else:
        print "Something went wrong"