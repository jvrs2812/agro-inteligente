from yolov5 import detect

if __name__ == '__main__':
    detect.run(imgsz=720, weights='F:\\projetos\\agro-inteligente\\yolo\\9000.pt',
               source='C:\\Users\\jvrs_\\Downloads\\WhatsApp Image 2023-09-23 at 22.21.09.jpeg',
               view_img=False)
