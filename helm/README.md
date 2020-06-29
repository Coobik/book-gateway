# book-chart - helm chart

## helm

### helm repo

- `helm repo add stable https://kubernetes-charts.storage.googleapis.com/`
- `helm repo add bitnami https://charts.bitnami.com/bitnami`

- `helm repo update`
- `helm repo list`


### helm search

- `helm search repo stable`
- `helm search repo bitnami`
- `helm search hub`


### helm show

- `helm show chart bitnami/redis`
- `helm show values bitnami/redis`


### helm install

- `helm install redis-service bitnami/redis --namespace data-ns`
    ```
    Redis can be accessed via port 6379 on the following DNS names from within your cluster:

    redis-service-master.data-ns.svc.cluster.local for read/write operations
    redis-service-slave.data-ns.svc.cluster.local for read-only operations

    To get your password run:

        export REDIS_PASSWORD=$(kubectl get secret --namespace data-ns redis-service -o jsonpath="{.data.redis-password}" | base64 --decode)

    To connect to your Redis server:

    1. Run a Redis pod that you can use as a client:
       kubectl run --namespace data-ns redis-service-client --rm --tty -i --restart='Never' \
        --env REDIS_PASSWORD=$REDIS_PASSWORD \
       --image docker.io/bitnami/redis:6.0.5-debian-10-r15 -- bash

    2. Connect using the Redis CLI:
       redis-cli -h redis-service-master -a $REDIS_PASSWORD
       redis-cli -h redis-service-slave -a $REDIS_PASSWORD

    To connect to your database from outside the cluster execute the following commands:

        kubectl port-forward --namespace data-ns svc/redis-service-master 6379:6379 &
        redis-cli -h 127.0.0.1 -p 6379 -a $REDIS_PASSWORD
    ```

- `kubectl get secret --namespace data-ns redis-service -o jsonpath="{.data.redis-password}" | base64 --decode`

- `kubectl get secret --namespace data-ns redis-service -o yaml`
    ```
    data:
      redis-password: emFScUk3R2k0bQ==
    ```

- `helm upgrade redis-service bitnami/redis --namespace data-ns`
- `helm status redis-service --namespace data-ns`
- `helm get values redis-service --namespace data-ns`

- `helm ls --namespace data-ns`
    ```
    NAME            NAMESPACE   REVISION    UPDATED                                 STATUS      CHART           APP VERSION
    redis-service   data-ns     2           2020-06-24 16:07:36.860308 +0300 EEST   deployed    redis-10.7.5    6.0.5
    ```

- `helm uninstall redis-service --namespace data-ns`
- `helm delete redis-service --namespace data-ns`


### helm create

- `helm create book-chart`
- `helm lint book-chart`

- `helm package book-chart`
    ```
    Successfully packaged chart and saved it to: /Users/.../book-gateway/helm/book-chart-0.1.0.tgz
    ```


## book-chart

`redis.password` must be base64 encoded

```
export REDIS_PASSWORD=$(kubectl get secret --namespace data-ns redis-service -o jsonpath="{.data.redis-password}")
helm install book-chart ./book-chart --set redis.password=$REDIS_PASSWORD --set redis.host=redis-service-master.data-ns.svc.cluster.local
```
