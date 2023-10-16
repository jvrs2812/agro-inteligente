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

def closest_color(rgb, color_palette, color_names):
    min_distance = math.inf
    closest_color = None

    for i, palette_color in enumerate(color_palette):
        distance = math.sqrt((palette_color[0] - rgb[0])**2 + (palette_color[1] - rgb[1])**2 + (palette_color[2] - rgb[2])**2)
        if distance < min_distance:
            min_distance = distance
            closest_color = color_names[i]

    return closest_color

class AnaliseCores:
    def __init__(self, r, g, b, x1, y1, x2, y2):
        self.rgb = (r, g, b)
        self.coordenadas = (x1, y1, x2, y2)

def anaylize(img):
    model = torch.hub.load('ultralytics/yolov5', 'custom', path='F:\\projetos\\agro-inteligente\\yolo\\300ep.pt')  # local model

    model.conf = 0.10  # NMS confidence threshold
    model.iou = 0.35  # NMS IoU threshold
    model.agnostic = False  # NMS class-agnostic
    model.multi_label = False  # NMS multiple labels per box
    model.classes = None  # (optional list) filter by class, i.e. = [0, 15, 16] for COCO persons, cats and dogs
    model.max_det = 1000  # maximum number of detections per image
    model.amp = False  # Automatic Mixed Precision (AMP) inference

    imgs = [img]  


    results = model(imgs)

    image = Image.open(BytesIO(requests.get(imgs[0]).content))

    # Extrair as coordenadas das frutas detectadas
    detections = results.pandas().xyxy[0]

    print(detections)

    # Para cada coordenada, calcular a média das cores em torno dessa coordenada
    average_colors = []
    image_arr = np.array(image)
    for _, detection in detections.iterrows():
        x1, y1, x2, y2 = detection[['xmin', 'ymin', 'xmax', 'ymax']]
        x1, y1, x2, y2 = int(x1), int(y1), int(x2), int(y2)  # Converter as coordenadas para inteiros
        cropped_img = image_arr[y1:y2, x1:x2]
        average_color = cropped_img.mean(axis=(0, 1))
        cv2.rectangle(image_arr, (x1, y1), (x2, y2), average_color.tolist(), 20)
        average_colors.append(AnaliseCores(int(average_color[0]), int(average_color[1]), int(average_color[2]), x1, y1, x2, y2))
    
    for obj in average_colors:
        print(f"RGB: {obj.rgb}, Coordenadas: {obj.coordenadas}")
        average_color = tuple(obj.rgb)  # Exemplo de cor média obtida
        rgb_array = average_color  # Já é uma tupla
        print(rgb_array)
        palette_array = [(204, 77, 47), (195, 129, 47), (255, 65, 47)]
        color_names = ['Madura', 'Verde', 'Passando do ponto']

        closest = closest_color(rgb_array, palette_array, color_names)
        print(closest)

        cv2.putText(image_arr, closest, (obj.coordenadas[0], obj.coordenadas[1]), cv2.LINE_AA, 10, 
                    (0, 255, 0), 5)
    
    image_with_boxes = Image.fromarray(image_arr)

    return image_with_boxes

def analyzeImage2(img):
    model = torch.hub.load('ultralytics/yolov5', 'custom', path='./300ep.pt',  pretrained=True)  # local model

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

    print("chegou aqui")

    image = analyzeImage2(url)

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