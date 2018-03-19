# BKClientAPI

  

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
