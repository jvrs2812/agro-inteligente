from yolov5 import train

if __name__ == '__main__':
    train.run(imgsz=416, data='F:\\projetos\\agro-inteligente\\yolo\\data.yaml',
              weights='F:\\projetos\\agro-inteligente\\yolo\\yolov5s.pt', epochs=60, batch=60, workers=4)
