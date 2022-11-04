# DevOps Challenge



## Rquirements
* Minikube
* kubectl
* Ingress Addon

## Start Everything

_Note:_ Most of this process could be automated using bash or powershell scripts.

### Namespace
Let's create a namespace
```shell
$ kubectl apply -f namespace.yaml
namespace/devops-test created

$ kubectl get ns
NAME              STATUS   AGE
default           Active   2m44s
devops-test       Active   14s
ingress-nginx     Active   2m10s
kube-node-lease   Active   2m46s
kube-public       Active   2m46s
kube-system       Active   2m46s
```

### Start Jenkins
Please follow these steps:
```shell
# create the deployment
$ kubectl apply -f ./builder/k8s/jenkins.deployment.yaml
deployment.apps/jenkins created
# create the service
$ kubectl apply -f ./builder/k8s/jenkins.service.yaml
service/jenkins-service created
# create the ingress
$ kubectl apply -f ./builder/k8s/jenkins.ingress.yaml
ingress.networking.k8s.io/jenkins-ingress created

# Once the deployment, service and ingress has been deployed
# we can view the pods using
$ kubectl get pods -n devops-test
NAME                       READY   STATUS    RESTARTS   AGE
jenkins-6978489dc6-b2rn5   1/1     Running   0          2m50s

# With the POD name (jenkins-6978489dc6-b2rn5) at hand we
# can now view it's logs using
$ kubectl logs -f jenkins-6978489dc6-b2rn5 -n devops-test
Running from: /usr/share/jenkins/jenkins.war
webroot: EnvVars.masterEnvVars.get("JENKINS_HOME")
[...]
```

Now to access Jenkins we need to add the cluster ip to the `/etc/hosts` file and add the DNS name we'd like to use to access this service which in our case is `builder.localhost.com`

```shell
$ minikube ip
172.17.19.15

$ cat /etc/hosts
[...]
172.17.19.15 minikube.mshome.net # 2022 11 5 11 13 1 24 238
172.17.19.15 builder.localhost.com api-builder.localhost.com
[...]
```

Once that is done, we can now access Jenkins by going to http://builder.localhost.com/


### Spring Boot w/ Swagger

Now we need to start spring boot application, to do so we'll follow these steps:
```shell
# deploy the mysql
$ kubectl apply -f ./api-builder/k8s/mysql.deployment.yaml
deployment.apps/mysql created
# deploy the mysql service
$ kubectl apply -f ./api-builder/k8s/mysql.service.yaml
service/mysql-service created
# we can we the pods
$ kubectl get pods -n devops-test
NAME                       READY   STATUS    RESTARTS   AGE
jenkins-6978489dc6-b2rn5   1/1     Running   0          11m
mysql-c4854bbc8-89z4n      1/1     Running   0          83s

# deploy the spring boot application
$ kubectl apply -f ./api-builder/k8s/spring.deployment.yaml
deployment.apps/spring-boot created
# deploy the spring service
$ kubectl apply -f ./api-builder/k8s/spring.service.yaml
service/spring-service created
# deploy the spring ingress
$ kubectl apply -f ./api-builder/k8s/spring.ingress.yaml
ingress.networking.k8s.io/spring-ingress created
```

If we have already added `api-builder.localhost.com` to the `/etc/hosts`, we can now access the Spring application on the browser by going to http://api-builder.localhost.com/swagger-ui/

## Issues faced

Here is a list of issues I've faced and why I could not deliver some of the requested features.

_Note_: Most of these issues are related to me being short on time.

### Persistent Volumes
Had issues with minikube on persistent mounts so ended up removing the persisten volumes from Jenkins. So this means that the data will be lost on each start, hence you will be asked to re-install Jenkins with password from the logs.

### Pre-Configure Jenkins Job
While I tried to have a pre-cofigured Jenkins Job I ran in to some difficulties with the API Token, which is related to the last point data not being persistent so on each start the API Keys will be removed, hence not being able to automate the process. This is what is would look like if we could import it like: 

```shell
$ cat builder/jobs/api-builder/config.xml | curl -X POST 'http://builder.localhost.com/createItem?name=api-builder' --header "Content-Type: application/xml" -d @-
```

The `config.xml` file contains all the configuration data that a job in Jenkins need to work.

### Communication between Spring and Jenkins
This is also related to the last issue, we cannot communicate we Jenkins API without a API Token, which will be different on each start.

But the basic API call from Spring to Jenkins would look like something:
```
GET http://builder.localhost.com/job/api-builder/build?token=<token>
```

`<token>` is configured in the `config.xml` file.

### Configure 2 separate Clusters
I was able to create 2 clusters using `minikube start -p <cluster_name>`, but did not know how to start everything on both clusters at the same time. Another reason it was not possible for me to do this was related to persistent volumes.

### Dockerfile in K8S
Not sure if there is a possibility to build the image inside the Deployment kind, like in `docker-compose` we can build the image before starting the container. As a work around to this issue I ended up uploading the image to docker hub repository.

I'm sure there are a lot of issues with this repo, if you find anything that would help me improve, please let me know.