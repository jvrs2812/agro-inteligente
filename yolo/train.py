from yolov5 import train

if __name__ == '__main__':
    train.run(imgsz=416, data='data.yaml',
              weights='yolov5s.pt', epochs=1000, batch=64, workers=4)
