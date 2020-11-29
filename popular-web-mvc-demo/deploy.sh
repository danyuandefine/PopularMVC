#!/bin/bash
#项目名称
IMAGE_NAME=$1
#将要要发布的版本
IMAGE_VERSION=$2
PJ_PORT=$3
SERVICE_NAME=$IMAGE_NAME
#如果未指定本地镜像仓库地址，则使用默认的地址
if [ ! -n '$SKYROAM_IMAGE_REPO' ]; then
    SKYROAM_IMAGE_REPO=192.168.1.180:5000
fi
#检查
echo $(sudo -E docker service ls | grep $IMAGE_NAME)
_count=$(sudo -E docker service ls | grep $IMAGE_NAME | wc -l)
if [ $_count -gt 0 ]; then
  #获取服务名
  SERVICE_NAME=$(sudo -E docker service ls | grep $IMAGE_NAME | awk '{print $2}')
  echo "sudo -E docker service update $SERVICE_NAME $SKYROAM_IMAGE_REPO/$IMAGE_NAME:$IMAGE_VERSION"
  sudo -E docker service update $SERVICE_NAME --image $SKYROAM_IMAGE_REPO/$IMAGE_NAME:$IMAGE_VERSION
else
  echo "sudo -E docker service create $IMAGE_NAME $SKYROAM_IMAGE_REPO/$IMAGE_NAME:$IMAGE_VERSION"
  if [ $PJ_PORT ]; then
    sudo -E docker service create --name $IMAGE_NAME \
      --network bridge --replicas 1 \
      --publish PJ_PORT \
      --restart-condition on-failure \
      --mount type=bind,src=/home/release/conf/,dst=/home/release/conf/ \
      --constraint 'node.role==worker' $SKYROAM_IMAGE_REPO/$IMAGE_NAME:$IMAGE_VERSION
  else
    sudo -E docker service create --name $IMAGE_NAME \
      --network bridge --replicas 1 \
      --restart-condition on-failure \
      --mount type=bind,src=/home/release/conf/,dst=/home/release/conf/ \
      --constraint 'node.role==worker' $SKYROAM_IMAGE_REPO/$IMAGE_NAME:$IMAGE_VERSION
  fi  
fi
