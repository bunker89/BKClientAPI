# BKClientAPI
클라이언트 수준에서 마이크로 서비스를 제공하기 위해 네트워크/비즈니스 간 처리 과정을 완전하게 분리시킨다.
비즈니스 로직을 한번 만들면 Socket, Http 등 다양한 네트워크 프로토콜로 연결시킬 수 있도록 한다.


#  Business 
Chainer 링크들을 묶기 위한 클래스
 └ BytesJSONChainer JSONAdapter를 Chainer로 묶을 수 있게 만드는 Chainer

NetHandle 네트워크에 들어갈 비즈니스 로직
 └ NetLink Chainer에 묶여져서 연속적으로 동작되는 NetHandle     └BytesJSONBridgeLink JSONAdapter를 NetLink처럼 사용할 수 있도록 하는 클래스

#  JSON
JSONAdapter JSON 로직과 네트워크 데이터타입관의 종속을 없애기 위해 만들어진 클래스

#  Network 
NIONetwork NIO 통신 클래스
 └SSLNIONetwork SSL 전송을 제공하는 NIONetwork

#  Peer 
PeerNIOClient BKFramework의 NIO Peer

#  Usage
1. Single Business
  NetHandle<byte[], byte[]> handle = ...
  Network<byte[], byte[]> network = new NIONetwork(handle, url, port);
  network.start();

2. Chained Business
  Chainer<byte[], byte[]> chainer = new Chainer(true);
  chainer.addChain(handle1);
  chainer.addChain(handle2);
  chainer.addChain(handle3);
  chainer.startNet(network);
  chainer.addChain(handle4);
  chainer.addChain(handle5);

3. network/JSON separation
...
  chainer.add(new BytesJSONBridgeLink(jsonAdapter));
...
