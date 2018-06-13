通过Java API方式连接ElasticSearch的两种方式：
    一种是建立客户端节点

    1, 创建一个node节点, 加入集群中, 通过这个node获取cilent (不建议使用) 这种方式相当于创建了一个节点, 不存储数据。

      Node node = NodeBuilder.nodeBuilder().clusterName("elasticsearch").client(true).settings(settings).node();
      Client client = node.client();

      .client(true) 说明该node节点是一个 es 客户端，而不是 node的服务端；不保存es的索引数据。
      如果.client(false) 说明这个节点是一个es 服务端，会存储 es 中的索引数据

      节点客户端以无数据节点(none data node)身份加入集群，换言之，它自己不存储任何数据，但是它知道数据在集群中的具体位置，并且能够直接转发请求到对应的节点上。

      节点客户端（Node client）
      节点客户端作为一个非数据节点加入到本地集群中。换句话说，它本身不保存任何数据，但是它知道数据在集群中的哪个节点中，并且可以把请求转发到正确的节点。

    一种是使用传输机客户端

    这个更轻量的传输客户端能够发送请求到远程集群。它自己不加入集群，只是简单转发请求给集群中的节点。
    Java客户端都通过9300端口与集群交互，使用Elasticsearch传输协议(Elasticsearch Transport Protocol)。集群中的节点之间也通过9300端口进行通信。
    如果此端口未开放，你的节点将不能组成集群。

    传输客户端（Transport client）
    轻量级的传输客户端可以将请求发送到远程集群。它本身不加入集群，但是它可以将请求转发到集群中的一个节点上。
    两个 Java 客户端都是通过 9300 端口并使用 Elasticsearch 的原生 传输 协议和集群交互。集群中的节点通过端口 9300 彼此通信。
    如果这个端口没有打开，节点将无法形成一个集群。