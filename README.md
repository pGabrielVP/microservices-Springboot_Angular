# Spring Boot Microservice

Aplicação seguindo a arquitetura de microserviços usando springboot

## Arquitetura do sistema

<img width="1328" height="672" alt="356532380-d4ef38bd-8ae5-4cc7-9ac5-7a8e5ec3c969" src="https://github.com/user-attachments/assets/d8789e31-2cae-47d0-89ec-6fb26c5358d3" />

## Tech Stack

- Spring Boot
- Angular
- Mongo DB
- MySQL
- Kafka
- Keycloak
- Test Containers with Wiremock
- Grafana Stack (Prometheus, Grafana, Loki and Tempo)
- API Gateway using Spring Cloud Gateway MVC
- Kubernetes

## Executando o backend

Requisitos:

- Java 21
- Docker
- Kubectl
- Kind Cluster - https://kind.sigs.k8s.io/docs/user/quick-start/#installation

### Inicie o cluster do kubernetes usando Kind

Execute o script k8s/kind/create-kind-cluster.ps1 para criar o cluster Kubernetes

```shell
./k8s/kind/create-kind-cluster.ps1
```

### Deploy as dependências da aplicação

```shell
kubectl apply -f k8s/manifests/infrastructure.yaml
```

### Deploy a aplicação 

```shell
kubectl apply -f k8s/manifests/applications.yaml
```

### Atualize o arquivo de hosts do sistema

127.0.0.1	keycloak.default.svc.cluster.local 

### Portforward dos serviços

```shell
kubectl port-forward svc/gateway-service 9000:9000
```

```shell
kubectl port-forward svc/frontend 4200:80
```

```shell
kubectl port-forward svc/grafana 3000:3000
```

tutorial [src](https://www.youtube.com/watch?v=yn_stY3HCr8)
