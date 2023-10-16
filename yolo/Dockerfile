
FROM arm64v8/ubuntu:18.04

# Atualize o índice de pacotes e instale as dependências
RUN apt-get update && \
    apt-get install -y \
    git \
    wget \
    unzip \
    build-essential \
    zlib1g-dev \
    libncurses5-dev \
    libgdbm-dev \
    libnss3-dev \
    libssl-dev \
    libreadline-dev \
    libffi-dev \
    libsqlite3-dev \
    libbz2-dev

WORKDIR /tmp
RUN wget https://www.python.org/ftp/python/3.11.5/Python-3.11.5.tar.xz && \
    tar -xf Python-3.11.5.tar.xz && \
    cd Python-3.11.5 && \
    ./configure --enable-optimizations && \
    make -j$(nproc) && \
    make install && \
    cd .. && \
    rm -rf Python-3.11.5*

# Instale o pip para o Python 3.11.5
RUN wget https://bootstrap.pypa.io/get-pip.py && python3.11 get-pip.py && rm get-pip.py

WORKDIR /app

# Copiar os arquivos necessários para o contêiner
COPY requirements.txt .
COPY detecttorch.py .
COPY 300ep.pt .

# Instale as dependências Python
RUN pip3 install -U -r requirements.txt

RUN apt-get install -y libgl1-mesa-glx

RUN apt-get install -y libglib2.0-0

RUN apt-get install -y libgl1

RUN apt-get install -y liblzma-dev

CMD ["python3", "detecttorch.py"]
