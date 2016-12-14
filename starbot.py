import os
import time
import re
import string
import random
import requests

from slackclient import SlackClient
from imgurpython import ImgurClient

IMGUR_CLIENT_ID = os.environ.get('IMGUR_CLIENT_ID')
IMGUR_CLIENT_SECRET = os.environ.get('IMGUR_CLIENT_SECRET')

BOT_ID = os.environ.get("BOT_ID")
READ_WEBSOCKET_DELAY = 1
BOT_TOKEN = os.environ.get('SLACK_BOT_TOKEN')
BOT_DM_STRING = '<@' + BOT_ID + '>'
TEALC_GIF = 'http://4.bp.blogspot.com/-TahRr7ackxY/UU38wPEpacI/AAAAAAAALGA/a8DAVIQYLD0/s1600/indeed.gif'
VADER_GIF = 'http://49.media.tumblr.com/0794b0f2331f54400118a38cec2bdadd/tumblr_o0gmnacvmB1tu6tfso1_500.gif'

slack_client = SlackClient(BOT_TOKEN)
imgurClient = ImgurClient(IMGUR_CLIENT_ID, IMGUR_CLIENT_SECRET)


def image(query):
    gallery = imgurClient.gallery_search(q=query)
    return random.choice(gallery).link


def pugBomb(sampleSize=1):
    page = random.randint(0, 5)
    gallery = imgurClient.gallery_search(q='pugs', page=page)
    length = len(gallery)
    if sampleSize > length:
        sampleSize = length
    pugs = random.sample(gallery, sampleSize)
    pugText = ''
    for pug in pugs:
        pugText += pug.link + '\n'
    return pugText


def xkcd(comic=0):
    if not comic:
        request = requests.get('http://xkcd.com/info.0.json')
        json = request.json()
        maxComic = json['num']
        comic = random.randint(1, maxComic)
    if comic == 'recent':
        request = requests.get('http://xkcd.com/info.0.json')
        json = request.json()
        return json['img']
    request = requests.get('http://xkcd.com/' + str(comic) + '/info.0.json')
    json = request.json()
    return json['img']


def handleMessage(message):
    if re.search('^' + BOT_DM_STRING, message, re.IGNORECASE):
        handleDirectMessage(message.replace(BOT_DM_STRING, ''))
    else:
        handleNormalMessage(message)


def handleDirectMessage(message):
    if re.search('pug bomb', message, re.IGNORECASE):
        explode = string.split(message.strip(), ' ')
        if len(explode) == 2:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text=pugBomb(), as_user=True)
        elif len(explode) == 3:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text=pugBomb(int(explode[2])),
                                  as_user=True)
        else:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text='Invalid Pug Bomb Format pupper!',
                                  as_user=True)

    if re.search('xkcd', message, re.IGNORECASE):
        explode = string.split(message.strip(), ' ')
        if len(explode) == 1:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text=xkcd(), as_user=True)
        elif len(explode) == 2:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text=xkcd(explode[1]), as_user=True)
        else:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text='Invalid XKCD Format!',
                                  as_user=True)

    if re.search('image me', message, re.IGNORECASE):
        explode = string.split(message.strip(), ' ')
        if len(explode) == 3:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text=image(str(explode[2])),
                                  as_user=True)
        else:
            slack_client.api_call("chat.postMessage", channel=read['channel'], text='Invalid query format!',
                                  as_user=True)


def handleNormalMessage(message):
    if re.search('indeed', message, re.IGNORECASE):
        slack_client.api_call("chat.postMessage", channel=read['channel'], text=TEALC_GIF, as_user=True)
    if re.search('disturbing', message, re.IGNORECASE) or re.search('faith', message, re.IGNORECASE):
        slack_client.api_call("chat.postMessage", channel=read['channel'], text=VADER_GIF, as_user=True)


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
