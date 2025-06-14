import os
import torch
from PIL import Image
import numpy as np
import requests
from io import BytesIO
import cv2
import matplotlib.pyplot as plt
import math
import pika
from PIL import Image, ImageDraw
from io import BytesIO
import base64
import io
from dotenv import load_dotenv
import os


def analyzeImage(img):
    model = torch.hub.load('ultralytics/yolov5', 'custom', path='./300ep.pt')  # local model

    model.conf = 0.10  # NMS confidence threshold
    model.iou = 0.35  # NMS IoU threshold
    model.agnostic = False  # NMS class-agnostic
    model.multi_label = False  # NMS multiple labels per box
    model.classes = None  # (optional list) filter by class, i.e., [0, 15, 16] for COCO persons, cats, and dogs
    model.max_det = 1000  # maximum number of detections per image
    model.amp = False  # Automatic Mixed Precision (AMP) inference

    # Carregar a imagem original
    image = Image.open(BytesIO(requests.get(img).content))

    results = model(image)
    results.ims # array of original images (as np array) passed to model for inference
    results.render()  # updates results.ims with boxes and labels
    for im in results.ims:
        buffered = BytesIO()
        im_base64 = Image.fromarray(im)
    return im_base64

    

def image_to_base64(image):
    # Salve a imagem em memória como bytes
    image_bytes = io.BytesIO()
    image.save(image_bytes, format='PNG')
    # Converta os bytes para base64
    image_base64 = base64.b64encode(image_bytes.getvalue()).decode('utf-8')
    return image_base64

def callback(ch, method, properties, body):
    # Decode the URL from the message body
    url = body.decode('utf-8')

    print(url)

    image = analyzeImage(url)

    image_base64 = image_to_base64(image)
    
    ch.basic_publish(exchange='', routing_key=properties.reply_to, properties=pika.BasicProperties(correlation_id = properties.correlation_id), body=  image_base64)

load_dotenv()
# RabbitMQ connection parameters
connection_params = pika.ConnectionParameters(
    host= '168.75.97.231',
    port=5672,  # default port
    virtual_host='/',
    credentials=pika.PlainCredentials('guest', 'guest')
)

# Create a connection to RabbitMQ
connection = pika.BlockingConnection(connection_params)

# Create a channel
channel = connection.channel()

# Set up the callback function to handle incoming messages
channel.basic_consume(queue='analyze', on_message_callback=callback, auto_ack=True)

# Start consuming messages
print('Waiting for messages. To exit press CTRL+C')
channel.start_consuming()