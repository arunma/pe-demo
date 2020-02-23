1. Install [Docker](https://docs.docker.com/)
1. Install [Minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)
2. Install [Istio](https://istio.io/docs/setup/#downloading-the-release)
3. Create the cluster:

        minikube addons enable ingress ; minikube start --cpus 4 --memory 8192
        
4. Install Istioctl
 
        brew install istioctl 

5. Install Istio on your cluster
          
        istioctl manifest apply --set profile=demo

6. To follow the Istio health in your cluster, run the following command: 

        kubectl get pod -n istio-system

7. When the status of all components above are `Completed` or `Running`, run the following command to enable sidecar injection:

        kubectl label namespace default istio-injection=enabled

8. To install the Load Balancer MetalLB, run the following command: 

        kubectl apply -f https://raw.githubusercontent.com/google/metallb/v0.8.3/manifests/metallb.yaml
        
   Let all the services in the namespace `metallb-system` come up 

9. Run the following command to get the Minikube ip: 

        minikube ip

10. Copy the IP address from the above command and paste it in the addresses section of the `ConfigMap` manifest located in the following locations 

        cd pe-demo/maid-account-manager/kube/deployment.yaml
        cd pe-demo/payment-manager/kube/deployment.yaml

        apiVersion: v1
        kind: ConfigMap
        metadata:
          namespace: metallb-system
          name: config
        data:
          config: |
            address-pools:
            - name: custom-ip-space
              protocol: layer2
              addresses:
              - 192.168.64.4/28 [REPLACE THIS IP. RETAIN THE CLASS OF THE CIDR BLOCK. eg. /28]

11. To deploy the Version 1 of both the `maid-account-manager` and `payment-manager` services, run the following command:

        kubectl apply -f deployment.yaml

12. To run the canary deployment, use the following command: 

        kubectl apply -f canary.yaml

12. To rollout Version 2 of the `maid-account-manager`, use the following command: 

        kubectl apply -f rollout_v2.yaml
