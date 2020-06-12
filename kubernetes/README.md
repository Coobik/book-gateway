# kubernetes deployment

## minikube

- `brew install minikube`

- `minikube start`
    ```
    😄  minikube v1.11.0 on Darwin 10.13.6
    ✨  Using the virtualbox driver based on existing profile
    👍  Starting control plane node minikube in cluster minikube
    🔄  Restarting existing virtualbox VM for "minikube" ...
    🐳  Preparing Kubernetes v1.18.2 on Docker 19.03.8 ...
    🔎  Verifying Kubernetes components...
    🌟  Enabled addons: dashboard, default-storageclass, storage-provisioner
    🏄  Done! kubectl is now configured to use "minikube"
    ```

- `minikube status`
    ```
    minikube
    type: Control Plane
    host: Running
    kubelet: Running
    apiserver: Running
    kubeconfig: Configured`
    ```

- `minikube ip`
    ```
    192.168.99.100
    ```

- `minikube ssh`
    ```
                             _             _
                _         _ ( )           ( )
      ___ ___  (_)  ___  (_)| |/')  _   _ | |_      __
    /' _ ` _ `\| |/' _ `\| || , <  ( ) ( )| '_`\  /'__`\
    | ( ) ( ) || || ( ) || || |\`\ | (_) || |_) )(  ___/
    (_) (_) (_)(_)(_) (_)(_)(_) (_)`\___/'(_,__/'`\____)

    $ pwd
    /home/docker
    $ exit
    logout
    ```

- `minikube dashboard`
    ```
    🤔  Verifying dashboard health ...
    🚀  Launching proxy ...
    🤔  Verifying proxy health ...
    🎉  Opening http://127.0.0.1:52823/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/ in your default browser...
    ```

- `minikube service list`
    ```
    |----------------------|------------------------------|--------------|-----------------------------|
    |      NAMESPACE       |             NAME             | TARGET PORT  |             URL             |
    |----------------------|------------------------------|--------------|-----------------------------|
    | book-ns              | bff-authors-service          | No node port |
    | book-ns              | bff-books-service            | No node port |
    | book-ns              | bff-brief-aggregator-service | http/2105    | http://192.168.99.100:31128 |
    | book-ns              | bff-frontend-service         | No node port |
    | book-ns              | bff-gateway-service          | http/8080    | http://192.168.99.100:30497 |
    | book-ns              | bff-web-sockets-service      | No node port |
    | data-ns              | redis-service                | No node port |
    | default              | kubernetes                   | No node port |
    | kube-system          | kube-dns                     | No node port |
    | kubernetes-dashboard | dashboard-metrics-scraper    | No node port |
    | kubernetes-dashboard | kubernetes-dashboard         | No node port |
    |----------------------|------------------------------|--------------|-----------------------------|
    ```

- `minikube addons list`
    ```
    |-----------------------------|----------|--------------|
    |         ADDON NAME          | PROFILE  |    STATUS    |
    |-----------------------------|----------|--------------|
    | ambassador                  | minikube | disabled     |
    | dashboard                   | minikube | enabled ✅   |
    | default-storageclass        | minikube | enabled ✅   |
    | efk                         | minikube | disabled     |
    | freshpod                    | minikube | disabled     |
    | gvisor                      | minikube | disabled     |
    | helm-tiller                 | minikube | disabled     |
    | ingress                     | minikube | disabled     |
    | ingress-dns                 | minikube | disabled     |
    | istio                       | minikube | disabled     |
    | istio-provisioner           | minikube | disabled     |
    | logviewer                   | minikube | disabled     |
    | metallb                     | minikube | disabled     |
    | metrics-server              | minikube | disabled     |
    | nvidia-driver-installer     | minikube | disabled     |
    | nvidia-gpu-device-plugin    | minikube | disabled     |
    | olm                         | minikube | disabled     |
    | registry                    | minikube | disabled     |
    | registry-aliases            | minikube | disabled     |
    | registry-creds              | minikube | disabled     |
    | storage-provisioner         | minikube | enabled ✅   |
    | storage-provisioner-gluster | minikube | disabled     |
    |-----------------------------|----------|--------------|
    ```

- `minikube stop`

- `minikube delete`
- `rm -rf ~/.minikube`


## docker

- `minikube docker-env`
    ```
    export DOCKER_TLS_VERIFY="1"
    export DOCKER_HOST="tcp://192.168.99.100:2376"
    export DOCKER_CERT_PATH="/Users/username/.minikube/certs"
    export MINIKUBE_ACTIVE_DOCKERD="minikube"

    # To point your shell to minikube's docker-daemon, run:
    # eval $(minikube -p minikube docker-env)
    ```

- `eval $(minikube docker-env)`

- `docker container prune`
    ```
    WARNING! This will remove all stopped containers.
    Are you sure you want to continue? [y/N] y
    ```


## kubectl

### kubectl config

- `kubectl config view`

- `kubectl get nodes`
    ```
    NAME       STATUS   ROLES    AGE   VERSION
    minikube   Ready    master   18d   v1.18.2
    ```

- `kubectl cluster-info`
    ```
    Kubernetes master is running at https://192.168.99.100:8443
    KubeDNS is running at https://192.168.99.100:8443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

    To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.
    ```

### kubectl get resources

- `kubectl get all -n book-ns`
- `kubectl get all -n data-ns`

- `kubectl get services -n book-ns`
    ```
    NAME                           TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
    bff-authors-service            ClusterIP   10.102.86.59     <none>        8080/TCP         10d
    bff-books-service              ClusterIP   10.105.235.253   <none>        8080/TCP         10d
    bff-brief-aggregator-service   ClusterIP   10.97.145.246    <none>        2105/TCP         10d
    bff-frontend-service           ClusterIP   10.109.0.126     <none>        1805/TCP         10d
    bff-gateway-service            NodePort    10.102.23.170    <none>        8080:30497/TCP   10d
    bff-web-sockets-service        ClusterIP   10.97.155.155    <none>        8080/TCP         10d
    brief-gateway-service          NodePort    10.103.57.247    <none>        8080:31876/TCP   10d
    ```

- `kubectl get services -n data-ns`
    ```
    NAME            TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)    AGE
    redis-service   ClusterIP   None         <none>        6379/TCP   2d17h
    ```

- `kubectl get namespaces`
    ```
    NAME                   STATUS   AGE
    book-ns                Active   10d
    data-ns                Active   10d
    default                Active   18d
    kube-node-lease        Active   18d
    kube-public            Active   18d
    kube-system            Active   18d
    kubernetes-dashboard   Active   17d
    ```

### kubectl create/update/delete resources

- `kubectl apply -f some.yaml -n some-ns`
- `kubectl delete -f some.yaml -n some-ns`

### kubectl scale down all

- `kubectl scale deployments -n book-ns --replicas=0 --all`
- `kubectl scale deployments -n data-ns --replicas=0 --all`
- `kubectl scale statefulsets -n data-ns --replicas=0 --all`

### kubectl delete all in default namespace

- `kubectl delete deployments --all`
- `kubectl delete pods --all`
- `kubectl delete statefulsets --all`
- `kubectl delete replicasets --all`

### kubectl exec

- `kubectl exec -it -n book-ns pod/bff-authors-575b755d7c-2p5dh -- /bin/sh`
    ```
    # pwd
    /
    # ls
    app.jar  bin  boot  dev  etc  home  lib  lib64  media  mnt  opt  proc  root  run  sbin  srv  sys  tmp  usr  var
    # exit
    ```


## deploy

`kubectl apply -f some.yaml -n some-ns`

### redis

- namespace: `data-ns.yaml`
- secret: `kubectl apply -f redis-secret.yaml -n data-ns`

- stateless:
    - deployment + service: `redis-deployment.yaml`

- stateful:
    - persistence:
        - persistent volume with host path under `/data` + persistent volume claim: `redis-persistentvolume.yaml`
        - persistent volume claim with standard storage class: `redis-persistentvolumeclaim-storageclass-standard.yaml`
    - stateful set + headless service (`clusterIP`: `None`): `redis-statefulset.yaml`

### services

- namespace: `book-ns.yaml`

- secrets:
    - `kubectl apply -f redis-secret.yaml -n book-ns`
    - `auth-secret.yaml`

- config maps:
    - `channels-configmap.yaml`
    - `redis-configmap.yaml`
    - `services-configmap.yaml`
    - `brief-services-configmap.yaml`

- deployments + services:
    - `authors-deployment.yaml`
    - `books-deployment.yaml`
    - `brief-aggregator-deployment.yaml`
    - `frontend-deployment.yaml`
    - `web-sockets-deployment.yaml`
    - `gateway-deployment.yaml`
    - `brief-gateway-deployment.yaml`


## ingress

- `minikube addons enable ingress`

- `kubectl apply -f gateway-ingress.yaml`

- `kubectl get ingress -n book-ns`
    ```
    NAME              CLASS    HOSTS   ADDRESS          PORTS   AGE
    gateway-ingress   <none>   *       192.168.99.100   80      20m
    ```
