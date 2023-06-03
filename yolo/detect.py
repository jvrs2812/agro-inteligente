from yolov5 import detect

if __name__ == '__main__':
    detect.run(imgsz=720, weights='runs/train/exp3/weights/best.pt',
               source='D:\\projeto\\grama.v1i.yolov5pytorch\\images.jpg',
               view_img=False)
